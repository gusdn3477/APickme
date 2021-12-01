import React, {Fragment} from "react";
import Header from "../../layout/Header";
import Footer from "../../layout/Footer";
import Bread from "../../elements/ui/Bread";
import CartTable from "../../elements/widgets/carttable/CartTable";


export default function Cart(){
    return(
     <Fragment>
        <Header/>   
        <Bread breadName ="Cart"/>
        <CartTable/>
        <Footer/>
    </Fragment>
    );

 }