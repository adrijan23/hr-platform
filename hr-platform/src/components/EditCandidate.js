import React, { useState, useEffect } from 'react';
import { Form, Button } from 'react-bootstrap';
import { useHistory, useParams } from 'react-router-dom';
import './component.css';

function EditCandidate() {
  
  const [id, setId] = useState(0);
  const [name, setName] = useState('');
  const [dob, setDob] = useState('');
  const [email, setEmail] = useState('');
  const [phone, setPhone] = useState('');
  const [skills, setSkills] = useState('');

  const { candidateId } = useParams();
  const history = useHistory();
  

  useEffect(() => {
    if (candidateId) {
      fetch(`http://localhost:8080/candidates/${candidateId}`)
        .then(response => response.json())
        .then(candidate => {
          setId(candidate.id);
          setName(candidate.name);
          setDob(candidate.dob);
          setEmail(candidate.email);
          setPhone(candidate.phone);
          setSkills(candidate.skills.map(skill => skill.name).join(","));
        })
        .catch((error) => console.error(error));
    }
  }, [candidateId]);
  

  const handleSubmit = (e) => {
    e.preventDefault();
    const data = { 
      id, 
      name, 
      dob, 
      email, 
      phone, 
      skills: skills.split(",").map(skill => ({ id: 0, name: skill.trim() })) 
    };

    fetch(`http://localhost:8080/candidates/update/${candidateId}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data)
    })
      .then(response => response.json())
      .then(data => {
        console.log(data.message);
        history.push('/candidate-list');
      })
      .catch((error) => console.error(error));
  }

  const handleCancel = () => {
    history.push('/candidate-list');
  }

  return (
    <div className='form'>
      <h1 style={{ textAlign: 'center' }}>Edit Candidate</h1>
      <Form onSubmit={handleSubmit}>
        <Form.Group controlId="name" className='form-group'>
          <Form.Label>Name:</Form.Label><br />
          <Form.Control type="text" value={name || ''} onChange={(e) => setName(e.target.value)} required />
        </Form.Group>
        <Form.Group controlId="dob" className='form-group'>
          <Form.Label>Date of Birth:</Form.Label><br />
          <Form.Control type="date" value={dob || ''} onChange={(e) => setDob(e.target.value)} required />
        </Form.Group>
        <Form.Group controlId="email" className='form-group'>
          <Form.Label>Email:</Form.Label><br />
          <Form.Control type="email" value={email || ''} onChange={(e) => setEmail(e.target.value)} required />
        </Form.Group>
        <Form.Group controlId="phone" className='form-group'>
          <Form.Label>Phone:</Form.Label><br />
          <Form.Control type="text" value={phone || ''} onChange={(e) => setPhone(e.target.value)} required />
        </Form.Group>
        <Form.Group controlId="skills" className='form-group'>
          <Form.Label>Skills (comma-separated):</Form.Label><br />
          <Form.Control type="text" value={skills} onChange={(e) => setSkills(e.target.value)} />
        </Form.Group>
        <div style={{ textAlign: 'center' }}>
          <button className='save-btn' onClick={handleSubmit}>Save candidate</button>
          <button variant="secondary" className='add-btn cancel' onClick={handleCancel}>Cancel</button>
        </div>
      </Form>
    </div>
  );
}

export default EditCandidate;
