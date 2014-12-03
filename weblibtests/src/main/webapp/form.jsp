<%@ taglib prefix="wl" uri="http://www.e-cosystems.org/taglib" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>


  
        <h3><wl:lang key="title" namespace="form"/></h3>

        <p><wl:input value="title" type="text" name="title" maxlength="150" class="data_name" persistent="true" /></p>

        <wl:ci name="title" rule="NotNull">
            <div class="have_to">NoTitle,<wl:lang key="fill_field" namespace="form"/></div>
        </wl:ci>


        <h3><wl:lang key="address" namespace="form"/></h3>

		<wl:input type="file" id="image" name="image" />
		
		
		<wl:ci name="image" rule="NotNull">
            <div class="have_to">NoFile</div>
        </wl:ci>
		
		<wl:ci name="image" rule="ValidImageFile">
            <div class="have_to">InvalidImageFile</div>
        </wl:ci>

        <p><wl:input value="17 rue paul bellamy nantes" type="text" id="address" name="address" persistent="true"  />
            <wl:ci name="address" rule="NotNull">
                <div class="have_to"><wl:lang key="fill_address" namespace="form" /></div>
            </wl:ci>

		
		
		


<!--         <div id="container_carte"> -->
<!--             <div id="cursormove"> -->
<%--                 <wl:lang key="cursor" namespace="form"></wl:lang> --%>
<!--             </div> -->
<!--             <div id="carte" style="width:100%; height:0px;"></div> -->
<!--         </div> -->



        <h3><wl:lang key="date" namespace="form" /></h3>

        <p><wl:input rel="tcal" type="text" readonly="readonly" name="date" id="date" persistent="true" /></p>
        <wl:ci name="date" rule="NotNull">
                <div class="have_to"><wl:lang key="fill_date" namespace="form" /></div>
            </wl:ci>

        <wl:ci name="date" rule="InvalidDate">
                <div class="have_to"><wl:lang key="invalid_date" namespace="form" /></div>
            </wl:ci>



        <h3><wl:lang key="hour" namespace="form" /></h3>


        <p id="time" ><wl:input type="time" size="5" maxlength="5" persistent="true" name="time" placeholder="hh:mm" /></p>


        <wl:ci name="time" rule="InvalidTime">
                <div class="have_to"><wl:lang key="fill_hour" namespace="form" /></div>
            </wl:ci>









        <h3><wl:lang key="description" namespace="form" /></h3>


        <p><wl:textarea name="description" class="data_desc" cols="50" rows="10" class="data_name" persistent="true"
                    placeholder="" /></p>

        <h3><wl:lang key="name" namespace="form" /></h3>

        <p><wl:input value="myname" type="text" name="name" maxlength="150" class="data_name" persistent="true" /></p>
        <wl:ci name="name"  rule="NotNull">
            <div class="have_to"><wl:lang key="fill_field" namespace="form" /></div>
        </wl:ci>


        <h3><wl:lang key="mail" namespace="form" /></h3>

        <p><wl:input type="text" name="mail" maxlength="150" class="data_name" />&nbsp;<wl:lang key="mail_prec" namespace="form" /></p>
        <wl:ci name="mail"  rule="NotNull">
            <div class="have_to"><wl:lang key="fill_field" namespace="form" /></div>
        </wl:ci>

        <wl:ci name="mail"  rule="InvalidMail">
            <div class="have_to"><wl:lang key="invalid_mail" namespace="form" /></div>
        </wl:ci>

<!--        <wl:reset><wl:lang key="reset" namespace="form" /></wl:reset>-->


<wl:lang namespace="general" key="your_lang" >
    <wl:langParameter name="lang">
        <wl:parameter name="lang" policy="ALL" />
    </wl:langParameter>
</wl:lang>


