package org.ecosystems.weblibtests.control;

import org.ecosystems.weblib.html.form.ArrayValidator;
import org.ecosystems.weblib.html.form.InputValidator;

public class CategorieArrayValidator extends ArrayValidator
{

    @Override
    protected InputValidator getItemValidator()
    {
        return new CategorieValidator();
    }

    @Override
    public String ruleName()
    {
        return "InvalidCategorieArray";
    }

}
