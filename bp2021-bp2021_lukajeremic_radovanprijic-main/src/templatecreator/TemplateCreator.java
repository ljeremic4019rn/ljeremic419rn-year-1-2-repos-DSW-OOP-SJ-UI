package templatecreator;

import templatecreator.templates.AbstractTemplate;

public interface TemplateCreator {
    AbstractTemplate createTemplate(String input);
}
