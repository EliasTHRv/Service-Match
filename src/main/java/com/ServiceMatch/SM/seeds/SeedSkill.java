package com.ServiceMatch.SM.seeds;

import com.ServiceMatch.SM.entities.Skill;
import com.ServiceMatch.SM.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class SeedSkill implements ApplicationRunner {
    @Autowired
    private SkillRepository skillRepository;

    public SeedSkill(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (skillRepository.count() != 0) {
        return ;
        }
        Skill skill = new Skill();
        skill.setName("Albañil");
        skill.setActive(true);
        ArrayList<Skill> skills = new ArrayList<>();
        skills.add(skill);
        skillRepository.save(skill);
    }
}