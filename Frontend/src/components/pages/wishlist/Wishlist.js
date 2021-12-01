
import Bread from "../../elements/ui/Bread";
import React, {Fragment} from "react";
import WishListTitle from "../../elements/ui/WishlistTitle";
import Header from "../../layout/Header";
import Footer from "../../layout/Footer";
import WishTable from "../../elements/widgets/wishtable/WishTable";

export default function Wishlist(){
    return(
        <Fragment>
        <Header/>
            <Bread breadName ="WishList"/>
            <WishTable/>
        <Footer/>
        </Fragment>
    );
}