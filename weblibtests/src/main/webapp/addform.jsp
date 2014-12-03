<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="wl" uri="http://www.e-cosystems.org/taglib" %>
<div class="article" style="border-radius: 28px 28px 28px 28px;">
	<wl:form postedKey="add" enctype="multipart/form-data"> 
          
          <h2>AJOUTER</h2>
          <div class="clr"></div>
          <div class="img"><img width="200" class="fl" alt="" src="images/add.png">
          </div>

          <div class="post_content">
		
		<h3>MERCI D'AJOUTER UNE DONNÉE UNIQUEMENT SI ELLE N'EST PAS DÉJÀ PRÉSENTE.<br><br>SI VOUS VOULEZ AJOUTER UNE TRADUCTION OU ÉDITER UNE DONNÉE, SUIVEZ CE LIEN <a href="http://www.e-cosystems.org/forum">LIEN</a></h3><br>

		<h3>NOM</h3>
		<h3>Entrer le nom du logo</h3>
		
		<wl:input persistent="true" type="TEXT" class="data_name" maxlength="200" name="name" />
<!-- 		<input type="text" class="data_name" value="" maxlength="200" name="data_name"> -->
		
		<wl:ci name="name" rule="NotNull">Vous devez entrer un nom</wl:ci>
		<wl:ci name="name" rule="InvalidHTML">vous ne pouvez pas entrer de code HTML dans le nom</wl:ci>
		
		<br>

		<h3>IMAGE</h3>
		<h3>Importer une image</h3>
		<wl:input type="FILE" persistent="false" name="image" />
<!-- 		<input type="file" name="textfield"> -->
		<wl:ci name="image" rule="NotNull">Vous devez entrer une image</wl:ci>
		<wl:ci name="image" rule="ValidImageFile">vous devez entrer un fichier image</wl:ci>
		
		
		<br><h3>LANGUE</h3>
		<h3>Quelle langue utilisez vous pour écrire la description suivante ?</h3>
		
		<wl:select name="lang">
			<wl:option value="en">English</wl:option>
			<wl:option value="fr">Français</wl:option>
		</wl:select><br />
		
		<wl:ci name="lang" rule="NotNull">Cette langue est indisponible</wl:ci>
		<wl:ci name="lang" rule="ValidLang">Cette langue est indisponible</wl:ci>
		
<!-- 		<select name="menu"> -->
<!-- 		<option value="#">Sélectionner une langue</option> -->
<!-- 		<option value="en">English</option> -->
<!-- 		<option value="fr">Français</option> -->
<!-- 		</select><br> -->

		<br><h3>INFORMATIONS</h3>
		<h3>Ecrivez des informations pertinentes sur le logo<br>(Ne pas copier et coller les informations si vous n'êtes pas propriétaires ou n'avez pas l'autorisation.</h3>
		<wl:textarea persistent="true" rows="10" cols="50" class="data_desc" name="comments" required="false" />
<!-- 		<textarea rows="10" cols="50" class="data_desc" name="data_text_desc"></textarea><br> -->
		
		<wl:ci name="comments" rule="NotNull">Vous devez entrer un nom</wl:ci>
		<wl:ci name="comments" rule="InvalidHTML">vous ne pouvez pas entrer de code HTML dans le nom</wl:ci>
		
		<br><h3>LIEN</h3>
		<h3>Lien vers un site pertinent<br>(exp : www.sitename.com)</h3>
		
		<wl:input type="text" class="data_link" value="" maxlength="200" name="link"/>
		
		<wl:ci name="link" rule="NotNull">Vous devez entrer un lien</wl:ci>
		<wl:ci name="link" rule="InvalidHTML">vous ne pouvez pas entrer de code HTML dans le lien</wl:ci>
<!-- 		<input type="text" class="data_link" value="" maxlength="200" name="data_link"><br> -->
		
		<h3>Contenu du lien<br>(exp : informations officielles...)</h3>
		<wl:input type="text" class="data_link2" value="" maxlength="200" name="linkInfo" />
		<wl:ci name="linkInfo" rule="NotNull">Vous devez entrer un lien</wl:ci>
		<wl:ci name="linkInfo" rule="InvalidHTML">vous ne pouvez pas entrer de code HTML dans le lien</wl:ci>
<!-- 		<input type="text" class="data_link2" value="" maxlength="200" name="data_link2"> -->
		
<!-- 		<button title="add_link" class="btn">Valider</button> -->
		<br><br><button title="add_link" class="btn">Ajouter un autre lien (ne fonctionne pas encore)</button><br>

		<br><h3>CATEGORIE</h3>
		<h3>Sélectionner une ou plusieurs catégories</h3>
		
		<wl:input type="checkbox" value="product" name="category" />Produits et services<br>
		<wl:input type="checkbox" value="alimentation" name="category" />Alimentation<br>
		
		
		
		<wl:mv name="category" policy="GET_POST">CategoryNoPosted</wl:mv>
		
		<wl:ci name="category" rule="EmptySet">EmptySet</wl:ci>
		<wl:ci name="category" rule="NotNull">NotNull</wl:ci>
<!-- 		<input type="checkbox" value="Products and services" name="data_type">Produits et services<br> -->
<!-- 		<input type="checkbox" value="Food" name="data_type">Alimentation<br> -->
		<input type="checkbox" value="Cosmetic" name="data_type">Cosmétique<br>
		<input type="checkbox" value="Cleaning product" name="data_type">Produit d'entretien<br>
		<input type="checkbox" value="Materials" name="data_type">Matériaux<br>
		<input type="checkbox" value="Packaging" name="data_type">Emballage<br>
		<input type="checkbox" value="Other" name="data_type">Autre<br>

		<br><h3>PORTÉE</h3>
		<input type="checkbox" value="Local" name="data_range">Locale<br>
		<input type="checkbox" value="National" name="data_range">Nationale<br>
		<input type="checkbox" value="International" name="data_range">Internationale<br>
		(The following parts appears depending on what have been quoted)
		<h3>Merci d'indiquer la ou les zones, séparés par une virgule<br>(exp : Californie, Nevada)</h3>
		<input type="text" class="data_range_local" value="" maxlength="500" name="data_range_local"><br>
		<h3>Merci d'indiquer le pays</h3>
		<input type="text" class="data_range_national" value="" maxlength="500" name="data_range_national"><br>
		<h3>Merci d'indiquer les pays, séparés par une virgule<br>(exp : Angleterre, Netherlands ou exp : Europe si le logo s'applique à un continent)</h3>
		<input type="text" class="data_range_international" value="" maxlength="500" name="data_range_international"><br>
		

		<br><h3>FORME</h3>
		<h3>Sélectionner une ou plusieurs formes</h3>
		<input type="checkbox" value="Circle" name="data_shape">Cercle<br>
		<input type="checkbox" value="Triangle" name="data_shape">Triangle<br>
		<input type="checkbox" value="Square" name="data_shape">Carré<br>
		<input type="checkbox" value="Rectangle" name="data_shape">Rectangle<br>
		<input type="checkbox" value="Oval" name="data_shape">Ovale<br>
		<input type="checkbox" value="Other" name="data_shape">Autre<br>

		<p align="center"> 
		
		<wl:submit id="add_logo">ENVOYER</wl:submit>
<!-- 		<button title="Submit Search" class="btn">Envoyer</button> -->
		
		</p>
          </div>
          <div class="clr"></div>
          
          
 	</wl:form>
 </div>