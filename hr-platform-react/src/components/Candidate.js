import React from "react";

function Candidate(props) {
  return (
    <div>
      <h3>{props.candidate.name}</h3>
      <p>Date of birth: {props.candidate.dob}</p>
      <p>Email: {props.candidate.email}</p>
      <p>Phone: {props.candidate.phone}</p>
      <p>Skills: </p>
      <ul>
        {props.candidate.skills.map((skill, index) => (
          <li key={index}>{skill.name}</li>
        ))}
      </ul>
    </div>
  );
}

export default Candidate;