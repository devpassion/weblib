package org.ecosystems.weblibtests.control;

import java.util.Set;

import org.ecosystems.weblib.html.form.Converter;
import org.ecosystems.weblib.html.form.SetConverter;
import org.ecosystems.weblibtests.model.Categorie;

public class CategorieSetConverter extends SetConverter<Categorie>
{
    private Set<Categorie> gabary;

    @Override
    protected Converter<Categorie> getElementConverter()
    {
        return new CategorieConverter();
    }

    @Override
    public Class<Set<Categorie>> getConvertClass()
    {
        // TODO Auto-generated method stub
        return null;
    }
}
