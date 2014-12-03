package org.ecosystems.weblibtests.control;

import org.ecosystems.weblib.html.form.FixItemValidator;
import org.ecosystems.weblibtests.model.Categorie;

public class CategorieValidator extends FixItemValidator<Categorie>
{

    @Override
    public String ruleName()
    {
        return "invalidCategorie";
    }

    @Override
    protected Categorie getFixItemInstance()
    {
        return Categorie.PRODUCT;
    }

}
