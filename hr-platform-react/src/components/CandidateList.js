import React, { useState, useEffect } from 'react';
import { Table, Button } from 'react-bootstrap';
import { useHistory } from 'react-router-dom';
import './component.css';

function CandidateList() {
  const [candidates, setCandidates] = useState([]);

  useEffect(() => {
    fetch('http://localhost:8080/candidates')
      .then(response => response.json())
      .then(data => setCandidates(data));
  }, []);


  const deleteCandidate = (id) => {
    fetch(`http://localhost:8080/candidates/delete/${id}`, {
      method: 'DELETE'
    })
      .then(response => response.json())
      .then(data => {
        console.log(data.message);
        // Remove the deleted candidate from the candidates array
        const updatedCandidates = candidates.filter(candidate => candidate.id !== id);
        // Update the state with the new list of candidates
        setCandidates(updatedCandidates);
      })
      .catch((error) => console.error(error));
  }

  

  const formatSkills = (skills) => {
    return skills.map(skill => skill.name).join(", ");
  }

  const history = useHistory();

  const handleClick = () => {
    history.push('/add-candidate');
  };
  const handleClickSkills = (id) => {
    history.push(`/add-candidate-skills/${id}`);
  }
  const handleClickEdit = (id) => {
    history.push(`/edit-candidate/${id}`);
  }

  const handleClickNameSearch = () => {
    history.push('/search-by-name');
  };

  const handleClickSkillSearch = () => {
    history.push('/search-by-skills');
  };

  return (
    <div>
      <div className='table-container'>
        <Table striped bordered hover>
          <thead>
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>DOB</th>
              <th>Email</th>
              <th>Phone</th>
              <th>Skills</th>
              <th></th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {candidates.map(candidate => (
              <tr key={candidate.id}>
                <td>{candidate.id}</td>
                <td>{candidate.name}</td>
                <td>{candidate.dob}</td>
                <td>{candidate.email}</td>
                <td>{candidate.phone}</td>
                <td>{formatSkills(candidate.skills)}</td>
                <td>
                  <Button className='add-skills-btn' variant="danger" onClick={() => handleClickSkills(candidate.id)}>
                    Update skills
                  </Button>
                </td>
                <td>
                  <Button className='edit-btn' variant="danger" onClick={() => handleClickEdit(candidate.id)}>
                    Edit
                  </Button>
                </td>
                <td>
                  <Button className='delete-btn' variant="danger" onClick={() => deleteCandidate(candidate.id)}>
                    Delete
                  </Button>
                </td>
              </tr>
            ))}
          </tbody>
        </Table>
        <div style={{ textAlign: 'center' }}>
          <button className='add-btn add' onClick={handleClick}>Add new candidate</button>
        </div>
      </div>
      <div className='search-btn-container'>
        <button onClick={handleClickNameSearch}>Search by Name</button>  
        <button onClick={handleClickSkillSearch}>Search by Skills</button>  
      </div>

    </div>

  );
}

export default CandidateList;
