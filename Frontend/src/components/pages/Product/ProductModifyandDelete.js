import React, {Fragment} from "react";
import Header from "../../layout/Header";
import Footer from "../../layout/Footer";
import Bread from "../../elements/ui/Bread";
import ProductModifiyAndDeleteForm from "./ProductModifyandDeleteForm";


export default function Cart(){
    return(
     <Fragment>
        <Header/>   
        <Bread breadName ="수정 및 삭제"/>
        <ProductModifiyAndDeleteForm/>
        <Footer/>
    </Fragment>
    );

 }