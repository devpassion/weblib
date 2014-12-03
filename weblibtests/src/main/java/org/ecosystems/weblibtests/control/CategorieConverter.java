package org.ecosystems.weblibtests.control;

import org.ecosystems.weblib.html.form.FixItemConverter;
import org.ecosystems.weblibtests.model.Categorie;

public class CategorieConverter extends FixItemConverter<Categorie>
{

    @Override
    public Class<Categorie> getConvertClass()
    {
        return Categorie.class;
    }

    @Override
    protected Categorie getFixItemInstance()
    {
        return Categorie.ALIMENTATION;
    }

}
