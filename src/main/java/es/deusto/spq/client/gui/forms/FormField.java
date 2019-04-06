package es.deusto.spq.client.gui.forms;

import java.util.ArrayList;
import java.util.List;

public class FormField {

    private FieldType fieldType;
    private String variable;
    private String translationKey;
    private List<ValidationRule> validationRules;

    public FormField(FieldType fieldType, String variable, String translationKey, ArrayList<ValidationRule> validationRules) {
        this.fieldType = fieldType;
        this.variable = variable;
        this.translationKey = translationKey;
        this.validationRules = validationRules;
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public String getTranslationKey() {
        return translationKey;
    }

    public void setTranslationKey(String translationKey) {
        this.translationKey = translationKey;
    }

    public List<ValidationRule> getValidationRules() {
        return validationRules;
    }

    public void setValidationRules(ArrayList<ValidationRule> validationRules) {
        this.validationRules = validationRules;
    }
}
