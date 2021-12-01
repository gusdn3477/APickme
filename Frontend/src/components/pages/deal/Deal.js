import React, { useState } from 'react';
import Title from '../../elements/ui/Title';
import TabMenu from '../../elements/ui/TabMenu';
import ProductView from '../../elements/widgets/product/ProductView';
import CategoryData from '../../../db/nav.json';

export default function Deal(){

    const [ categoryName , setCategoryName ] = useState("fashion");
    let sliceNumber = 8;
    let columnNumber = 4;
    console.log(categoryName);

    return(

        <section id="deal">
            <div className="container">
                <Title categoryName = {categoryName}/>
                <TabMenu 
                setCategoryName = {setCategoryName}
                categoryName = {categoryName}/>
                
                <ProductView 
                categoryName = {categoryName}
                sliceNumber = {sliceNumber}
                columnNumber= {columnNumber}
                />
               
            </div> 
        </section> 

    );
}