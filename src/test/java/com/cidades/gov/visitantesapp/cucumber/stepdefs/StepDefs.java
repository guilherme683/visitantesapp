package com.cidades.gov.visitantesapp.cucumber.stepdefs;

import com.cidades.gov.visitantesapp.VisitantesappApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = VisitantesappApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
