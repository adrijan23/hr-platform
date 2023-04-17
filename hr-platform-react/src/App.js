import React from 'react';
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';
import Navigation from './components/nav';
import CandidateList from './components/CandidateList';
import AddCandidate from './components/AddCandidate';
import AddSkills from './components/AddSkills';
import EditCandidate from './components/EditCandidate';
import SearchCandidatesByName from './components/SearchCandidatesByName';
import SearchCandidatesBySkill from './components/SearchCandidatesBySkill';

function App() {
  return (
    
    <div>
      <Navigation />
      <Router>
        
        <Switch>
          <Route exact path="/" component={CandidateList} />
          <Route exact path="/candidate-list" component={CandidateList} />
          <Route exact path="/add-candidate" component={AddCandidate} />
          <Route exact path="/add-candidate-skills/:id" component={AddSkills} />
          <Route exact path="/edit-candidate/:candidateId" component={EditCandidate} />
          <Route exact path="/search-by-name" component={SearchCandidatesByName} />
          <Route exact path="/search-by-skills" component={SearchCandidatesBySkill} />
        </Switch>
      </Router>
    </div>
  );
}

export default App;
