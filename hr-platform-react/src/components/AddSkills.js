import React, { useState, useEffect } from 'react';
import { useHistory, useParams } from 'react-router-dom';
import { Form, Button, Table } from 'react-bootstrap';
import './component.css';
import '../App.css';

function AddSkills(props) {
  const [skills, setSkills] = useState('');
  const [candidateSkills, setCandidateSkills] = useState([]);
  const { id } = useParams(); // retrieve candidate ID from page address
  const history = useHistory();
  

  useEffect(() => {
    fetch(`http://localhost:8080/candidates/${id}`)
      .then(response => {
        if (response.ok) {
          return response.json();
        }
        throw new Error('Failed to fetch candidate skills.');
      })
      .then(data => {
        console.log('Candidate skills retrieved:', data.skills);
        setCandidateSkills(data.skills);
      })
      .catch(error => console.error(error));
  }, [id]);

  const handleSkillsChange = (event) => {
    setSkills(event.target.value);
  }

  const handleSubmit = (event) => {
    event.preventDefault();
    if (!skills) {
      alert('Please fill out all required fields.');
      return;
    }
    const skillNames = skills.split(',').map(skillName => skillName.trim());
    const newSkills = skillNames.map(skillName => ({ id: 0, name: skillName }));
    const requestOptions = {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(newSkills)
    };
    fetch(`http://localhost:8080/candidates/${id}/add-skills`, requestOptions)
      .then(response => {
        if (response.ok) {
          return response.json();
        }
        throw new Error('Failed to add skills to candidate.');
      })
      .then(data => {
        console.log('Skills added to candidate:', data);
        setSkills(''); // clear the input field after successful submission
        return fetch(`http://localhost:8080/candidates/${id}`);
      })
      .then(response => {
        if (response.ok) {
          return response.json();
        }
        throw new Error('Failed to fetch candidate skills.');
      })
      .then(data => {
        console.log('Candidate skills retrieved:', data.skills);
        setCandidateSkills(data.skills);
      })
      .catch(error => console.error(error));
  }

  const handleCancel = () => {
    history.push('/candidate-list');
  }

  const removeSkill = (skillId) => {
    const requestOptions = {
      method: 'DELETE',
      headers: { 'Content-Type': 'application/json' },
    };
    fetch(`http://localhost:8080/candidates/${id}/skills/${skillId}`, requestOptions)
      .then(response => {
        if (response.ok) {
          return response.json();
        }
        throw new Error('Failed to remove skill from candidate.');
      })
      .then(data => {
        console.log('Skill removed from candidate:', data);
        setCandidateSkills(candidateSkills.filter(skill => skill.id !== skillId));
      })
      .catch(error => console.error(error));
  }
  

  return (
    <div>
        <h1>Add skills to candidate</h1>
        <p>Candidate ID: {id}</p>
        <div className='form'>
            <Form onSubmit={handleSubmit}>
                <Form.Group controlId="formSkills">
                <Form.Label>Enter new skills:</Form.Label><br />
                <Form.Control type="text" placeholder="Enter comma separated skills" onChange={handleSkillsChange} value={skills} />
                </Form.Group>
                <Button variant="primary" className='add-btn' type="submit">
                Submit
                </Button>
                <Button variant="secondary" className='add-btn' onClick={handleCancel} style={{ marginLeft: '10px' } }>
                Cancel
                </Button>
            </Form>
            <br />
        </div>
        <h2>Current skills for candidate </h2>
        <div className='table-container table-skills'>
            <Table striped bordered hover>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                
                </tr>
                </thead>
                <tbody>
                {candidateSkills.map(skill => (
                    <tr key={skill.id}>
                    <td>{skill.id}</td>
                    <td>{skill.name}</td>
                    <td>
                        <Button variant="danger" className='delete-btn' onClick={() => removeSkill(skill.id)}>Remove</Button>
                    </td>
                    </tr>
                ))}
                </tbody>
            </Table>
        </div>
        
    </div>
  );
}

export default AddSkills;
