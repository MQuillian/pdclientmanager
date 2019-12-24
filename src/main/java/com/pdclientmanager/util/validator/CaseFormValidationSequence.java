package com.pdclientmanager.util.validator;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

@GroupSequence({Default.class, DateOpenedBeforeClosedGroup.class})
public interface CaseFormValidationSequence {

}
