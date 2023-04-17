import React, { useState, useEffect } from 'react';
import { Form, Button, Table } from 'react-bootstrap';
import './component.css';
import { useHistory } from 'react-router-dom/cjs/react-router-dom.min';

function SearchCandidatesByName() {
  const [name, setName] = useState('');
  const [candidates, setCandidates] = useState([]);

  const handleSubmit = (e) => {
    e.preventDefault();
    fetch(`http://localhost:8080/candidates/search?name=${name}`)
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
      <h1>Search Candidates by Name</h1>
      <Form className='form' onSubmit={handleSubmit}>
        <Form.Group controlId="name">
          <Form.Label>Name:</Form.Label><br />
          <Form.Control type="text" value={name} placeholder='Enter full name' onChange={(e) => setName(e.target.value)} />
        </Form.Group>
        <Button variant="primary" className='submit-btn' type="submit">
          Search
        </Button>
        <Button variant="secondary" className='add-btn' onClick={handleCancel} style={{ marginLeft: '10px' } }>
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

export default SearchCandidatesByName;
