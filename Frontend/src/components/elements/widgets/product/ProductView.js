import React, {useState, useEffect} from 'react';
import {Link} from 'react-router-dom';
import Rating from '../../ui/Rating';

export default function ProductView({categoryName, sliceNumber , columnNumber}){

    let process = require('../../../../db/myProcess.json');

    const [newData, setnewData] = useState([]);
    // const [categoryLists , setCategoryList] = ({});

    useEffect(() => {
        fetch(`/catalog-service/catalogs`)
        .then(res => {
            return res.json();
        })
        .then(data => {
            setnewData(data);
            console.log(data);
        })
        
    },[process.IP, process.PORT]);

    const hanlePutWishList = (id) => {
        
        fetch(`/cart/${id}`)
        .then(res => {
            return res.json();
        })
        .then(data => {
            fetch(`http://${process.IP}:${process.PORT}/wish/`,{
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    id: data.id,
                    name: data.name,
                    image: data.image,
                    price: data.price,
                    discount: data.discount
                }),
            })
        }).then(
            alert("success")
        )
    }


    const handlePutCompareList = (id) => {

             fetch(`http://${process.IP}:${process.PORT}/product/${id}`)
        .then(res => {
            return res.json();
        })
        .then(data => {
            fetch(`http://${process.IP}:${process.PORT}/compare`,{
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    id: data.id,
                    name: data.name,
                    image: data.image,
                    price: data.price,
                    discount: data.discount,
                    shortDescription : data.shortDescription,
                    rating : data.rating
                }),
            })
        }).then(
            alert("success")
        )
    }

    //예외 발생 가능할 것 같음. 이후에 처리해보기
    const productList = newData.length > 0 ? newData.map((item, index) => (

        <div className= {`col-xl-${columnNumber} col-md-6 col-lg-3 col-sm-6 `} key={item.id}>
        <div className="product-wrap mb-25">
            <div className="product-img">
                <Link to={`/productdetail/${item.productId}`}>
                    <img className="default-img" src="assets/img/product/fashion/8.jpg" alt="" />
                    <img className="hover-img" src="/assets/img/product/fashion/6.jpg" alt="" />
                </Link>
                <div className="product-action">
                    <div className="pro-same-action pro-wishlist">
                        <button
                            value={item.id}
                            onClick={() => hanlePutWishList(item.id)}
                        >
                            <i className="las la-bookmark"></i>
                        </button>
                    </div>
                    <div className="pro-same-action pro-cart">
                        <button disabled="" className="active">Buy</button>
                    </div>
                    <div className="pro-same-action pro-quickview">
                        <button 
                            className="" 
                            title={item.id} 
                            onClick={() => handlePutCompareList(item.id)} 
                            value={item.id}
                        >
                            <i className="las la-eye"></i>
                        </button>
                    </div>
                </div>
            </div>
            {/* 여기부터 제목, 평점 부분 */}
            <div className="product-content text-center">

                <h3><Link to={`/productdetail/${item.productId}`}><div style={{color:"black"}}>{item.productName}</div></Link></h3>
                <div className="product-price">
                    <span>{item.unitPrice}</span> 
                </div>
            </div>
        </div>
    </div>
        

    )).slice(0,sliceNumber) : "";

    return(
        <div className="row mt-5">
            {productList}
        </div>
        
    );
}