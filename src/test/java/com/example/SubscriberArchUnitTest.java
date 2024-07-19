package com.example;

import com.example.service.NewStatusAutomationService;
import com.example.util.Subscriber;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;

import java.util.Objects;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(packages = "com.example")
public class SubscriberArchUnitTest {

    @ArchTest
    public void automationSubscribersMustOverrideDefaultOrderValueMethod(final JavaClasses classes) {
        classes().that()
                .resideInAPackage("..automation..")
                .and()
                .implement(Subscriber.class)
                .should(overrideTheDefaultOrderValueMethod())
                .because("automation Subscribers must override default orderValue method")
                .check(classes);
    }

    private static ArchCondition<JavaClass> overrideTheDefaultOrderValueMethod() {
        return new ArchCondition<>("override the default order value method") {
            @Override
            public void check(final JavaClass javaClass, final ConditionEvents conditionEvents) {
                conditionEvents.add(new SimpleConditionEvent(javaClass,
                        javaClass.getAllMethods()
                                .stream()
                                .filter(method -> Objects.equals("orderValue", method.getName()))
                                .count() > 1,
                        javaClass.getName()));
            }
        };
    }

    @ArchTest
    public void onlyAutomationSubscribersMayUseNewStatusAutomationService(final JavaClasses classes) {
        classes().that()
                .implement(NewStatusAutomationService.class)
                .should()
                .onlyBeAccessed()
                .byClassesThat()
                .resideInAPackage("..automation..")
                .because("only automation Subscribers may use the NewStatusAutomationService")
                .check(classes);
    }
}
