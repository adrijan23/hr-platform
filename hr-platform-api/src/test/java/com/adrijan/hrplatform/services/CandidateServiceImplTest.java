package com.adrijan.hrplatform.services;

import com.adrijan.hrplatform.models.Candidate;
import com.adrijan.hrplatform.repo.CandidateRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
class CandidateServiceImplTest {

    @Mock
    private CandidateRepo candidateRepo;

    @InjectMocks
    private CandidateServiceImpl candidateService;

    private Candidate candidate;

    private List<Candidate> candidates;


    @BeforeEach
    void setUp() {
        candidate = new Candidate();
        candidate.setDob(LocalDate.of(2000, 11, 3));
        candidate.setEmail("test.email@test.com");
        candidate.setName("test name");
        candidate.setPhone("1234567");
        candidate.setId(1L);
        candidates = new ArrayList<>();
        candidates.add(candidate);

    }

    @DisplayName("JUnit test for findByNameIgnoreCase method (positive scenario)")
    @Test
    void findByNameIgnoreCase_positive() {
        given(candidateRepo.findByNameIgnoreCase("Test Name"))
                .willReturn(candidates);

        List<Candidate> testCandidates = candidateService.findByNameIgnoreCase("Test Name");

        assertThat(testCandidates).isNotNull();
        assertThat(testCandidates.get(0).getPhone()).isEqualTo(candidate.getPhone());
    }

    @DisplayName("JUnit test for findByNameIgnoreCase method (negative scenario)")
    @Test
    void findByNameIgnoreCase_negative() {
        given(candidateRepo.findByNameIgnoreCase("Invalid Name"))
                .willReturn(null);

        List<Candidate> testCandidates = candidateService.findByNameIgnoreCase("Invalid Name");

        assertThat(testCandidates).isNull();
    }

    @DisplayName("JUnit test for findBySkillsNameIn method (positive scenario)")
    @Test
    void findBySkillsNameIn_positive() {
        List<String> skillsList = new ArrayList<>();
        skillsList.add("Some Skill A");
        given(candidateRepo.findBySkillsNameIn(skillsList))
                .willReturn(candidates);

        List<Candidate> testCandidates = candidateService.findBySkillsNameIn(skillsList);

        assertThat(testCandidates).isNotNull();
        assertThat(testCandidates.get(0).getPhone()).isEqualTo(candidate.getPhone());
    }

    @DisplayName("JUnit test for findBySkillsNameIn method (negative scenario)")
    @Test
    void findBySkillsNameIn_negative() {
        List<String> skillsList = new ArrayList<>();
        skillsList.add("Invalid Name");
        given(candidateRepo.findBySkillsNameIn(skillsList))
                .willReturn(null);

        List<String> skillsListSkillDoesNotExist = new ArrayList<>();
        skillsListSkillDoesNotExist.add(("Invalid Name"));
        List<Candidate> testCandidates = candidateService.findBySkillsNameIn(skillsListSkillDoesNotExist);

        assertThat(testCandidates).isNull();
    }

    @DisplayName("JUnit test for findAll method (positive scenario)")
    @Test
    void findAll_positive() {
        given(candidateRepo.findAll())
                .willReturn(candidates);

        List<Candidate> testCandidates = candidateService.findAll();

        assertThat(testCandidates).isNotNull();
        assertThat(testCandidates.get(0).getPhone()).isEqualTo(candidate.getPhone());
    }

    @DisplayName("JUnit test for findAll method (negative scenario)")
    @Test
    void findAll_negative() {
        given(candidateRepo.findAll())
                .willReturn(null);

        List<Candidate> testCandidates = candidateService.findAll();

        assertThat(testCandidates).isNull();
    }

    @DisplayName("JUnit test for findById method (positive scenario)")
    @Test
    void findById_positive() {
        Candidate candidate = candidates.get(0);
        given(candidateRepo.findById(1L)).willReturn(Optional.ofNullable(candidate));

        Optional<Candidate> testCandidates = candidateService.findById(1L);

        assertThat(testCandidates).isNotNull();
        testCandidates.ifPresent(value -> {
            assert candidate != null;
            assertThat(value.getPhone()).isEqualTo(candidate.getPhone());
        });

    }

    @DisplayName("JUnit test for findById method (negative scenario)")
    @Test
    void findById_negative() {
        Candidate candidate = candidates.get(0);
        given(candidateRepo.findById(1L)).willReturn(null);

        Optional<Candidate> testCandidates = candidateService.findById(1L);

        assertThat(testCandidates).isNull();
    }
}