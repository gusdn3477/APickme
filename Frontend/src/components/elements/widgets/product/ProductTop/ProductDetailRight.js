import React, { useState, useEffect } from 'react';
import {useParams} from 'react-router-dom';
import ProDetRgtTop from './ProDetRgtTop';
import ProDetRgtMiddle from './ProDetRgtMiddle';
import ProDetRgtBottom from './ProDetRgtBottom';

export default function ProductDetailRight({id}) {

    const [ datas, setDatas ] = useState([]);
    var process = require('../../../../../db/myProcess.json');

    useEffect(()=>{
        fetch(`/catalog-service/catalogs/${id}`)
        .then(res => {
            return res.json();
        })
        .then(data => {
            setDatas(data);
            console.log(data);
        });
    },[process.IP, process.PORT]);
    
    return (
        <div className="col-lg-6 col-md-6">
            <div className="product-details-content ml-70">

                <ProDetRgtTop 
                    name = {datas.productName}
                    price = {datas.unitPrice}
                    writer = {datas.writer}
                />
                <ProDetRgtMiddle
                    productId = {datas.productId}
                    productName = {datas.productName}
                    unitPrice = {datas.unitPrice}
                    stock = {datas.stock}
                    image = {datas.image}
                    qty = {datas.qty}
                    writer = {datas.writer}
                    createdAt = {datas.createdAt}
                    id = {id}
                />
                 <ProDetRgtBottom /> 

            </div>
        </div>
    );
}