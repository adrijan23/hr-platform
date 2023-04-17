import React, { useState } from 'react';
import { Form, Button, Table } from 'react-bootstrap';
import { useHistory } from 'react-router-dom';
import './component.css';

function SearchCandidatesBySkill() {
  const [skills, setSkills] = useState([]);
  const [candidates, setCandidates] = useState([]);

  const encodedSkills = skills.map(skill => encodeURIComponent(skill));

  const handleSubmit = (e) => {
    e.preventDefault();
    fetch(`http://localhost:8080/candidates/search-by-skill?skills=${encodedSkills.join(',')}`)
      .then(response => response.json())
      .then(candidates => {
        setCandidates(candidates);
      })
      .catch((error) => console.error(error));
  }

  const formatSkills = (skills) => {
    return skills.map(skill => skill.name).join(", ");
  }

  const history = useHistory();
  const handleCancel = () => {
    history.push('/candidate-list');
  }

  return (
    <div>
      <h1>Search Candidates by Skill</h1>
      <Form className='form' onSubmit={handleSubmit}>
        <Form.Group controlId="skills">
          <Form.Label>Skills:</Form.Label><br />
          <Form.Control type="text" value={skills} placeholder='Enter skills separated by comma' onChange={(e) => setSkills(e.target.value.split(','))} />
        </Form.Group>
        <Button variant="primary" className='submit-btn' type="submit">
          Search
        </Button>
        <Button variant="secondary" className='add-btn' onClick={handleCancel} style={{ marginLeft: '10px' }}>
          Cancel
        </Button>
      </Form>
      <div className='table-container'>
        <Table striped bordered hover>
          <thead>
            <tr>
              <th>Name</th>
              <th>Date of Birth</th>
              <th>Email</th>
              <th>Phone</th>
              <th>Skills</th>
            </tr>
          </thead>
          <tbody>
            {candidates.map(candidate => (
              <tr key={candidate.id}>
                <td>{candidate.name}</td>
                <td>{candidate.dob}</td>
                <td>{candidate.email}</td>
                <td>{candidate.phone}</td>
                <td>{formatSkills(candidate.skills)}</td>
              </tr>
            ))}
          </tbody>
        </Table>
      </div>
    </div>
  );
}

export default SearchCandidatesBySkill;
