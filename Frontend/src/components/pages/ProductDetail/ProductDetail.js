
import React, { Fragment, useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import Header from '../../layout/Header';
import Footer from '../../layout/Footer';
import Bread from '../../elements/ui/Bread';
import ProductTop from '../../elements/widgets/product/ProductTop/ProductTop';

export default function ProductDetail(){
    
    const { id } = useParams();
    const [ productData , setProductData ] = useState([]);

    let process = require('../../../db/myProcess.json');
  
    useEffect((id)=>{
        fetch(`/catalog-service/catalogs/${id}`)
        .then(res => {
            return res.json();
        })
        .then(data => {
            setProductData(data);
            console.log(data);
        });
    },[process.IP, process.PORT, id]);

    console.log("ProductDetail 의 propductData : " + productData);

    return (
        <Fragment>
            <Header/>
            <Bread
                breadId = {productData.id}
                breadName = '상품 상세보기'
                breadUrl = {`/productdetail/${productData.id}`}
            />
            <ProductTop
                id = {id}/>
            {/*<ProductBottom/>*/}
            <Footer/>
        </Fragment>
    );
}