package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view

        Job aJob = jobData.findById(id);
        String employer = aJob.getEmployer().getValue();
        String name = aJob.getName();
        String location = aJob.getLocation().getValue();
        String positionType = aJob.getPositionType().getValue();
        String coreCompetency = aJob.getCoreCompetency().getValue();


        model.addAttribute("employer", employer);
        model.addAttribute("name", name);
        model.addAttribute("location", location);
        model.addAttribute("positionType", positionType);
        model.addAttribute("coreCompetency", coreCompetency);

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute @Valid JobForm jobForm, Errors errors) {

        if (errors.hasErrors()) {

            return "new-job";

        } else {

            String name = jobForm.getName();
            Employer employer = jobData.getEmployers().findById(jobForm.getEmployerId());
            Location location = jobData.getLocations().findById(jobForm.getLocationId());
            PositionType positionType = jobData.getPositionTypes().findById(jobForm.getPositionTypeId());
            CoreCompetency coreCompetency = jobData.getCoreCompetencies().findById(jobForm.getCoreCompetencyId());

            Job newJob = new Job(name, employer, location, positionType, coreCompetency);
            jobData.add(newJob);

            model.addAttribute("employer", employer);
            model.addAttribute("name", name);
            model.addAttribute("location", location);
            model.addAttribute("positionType", positionType);
            model.addAttribute("coreCompetency", coreCompetency);

            // TODO #6 - Validate the JobForm model, and if valid, create a
            // new Job and add it to the jobData data store. Then
            // redirect to the job detail view for the new Job.

            return "job-detail";

        }



    }
}
