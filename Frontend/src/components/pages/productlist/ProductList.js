import React, { Fragment, useState, useEffect } from 'react';
import Header from '../../layout/Header';
import Footer from '../../layout/Footer';
import Bread from '../../elements/ui/Bread';
import SideBar from '../../elements/widgets/productlist/SideBar';
import Shop from '../../elements/widgets/productlist/Shop';

export default function JobsList(){

    const [categoryName , setCategoryName] = useState("");

    return(
        <Fragment>
            <Header/>
            <Bread
                breadName ="SHOP"
            />
           <div className="shop-area pt-95 pb-100">
                <div className="container">
                    <div className="row">
                        {/*}
                     <SideBar 
                        setCategoryName = {setCategoryName}
    />                  */}
                      <Shop
                      categoryName = {categoryName}
                      />

                     </div>
                </div>
            </div>
            <Footer/>    
        </Fragment>
    );
}