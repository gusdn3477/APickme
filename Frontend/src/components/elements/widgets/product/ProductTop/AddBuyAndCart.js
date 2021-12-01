import React, {useState, useEffect} from 'react';
import { Fragment } from 'react';
import {Link, useParams} from 'react-router-dom';
import { useHistory } from 'react-router';

export default function AddBuyAndCart({productId, productName, qty, unitPrice, stock, image, writer, id}) {

    let process = require('../../../../../db/myProcess.json');

    const [count, setCount] = useState(1);
    const gogo = useHistory();
    const handleCountAdd = () => {
        setCount(count+1);
    }

    const handleCountDec = () => {
        count > 1 ? setCount(count-1) : alert("최소 수량은 1개 입니다.")
    }

    const linktoBuy = (id) => {
        localStorage.setItem('qty', count);
        console.log(count);
        gogo.push(`/buy/${id}`);
    }

    const hanlePutCartList = () => {
        fetch(`/cart-service/${localStorage.getItem('userId')}/carts`,{
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                productId: productId,
                productName : productName,
                userId : localStorage.getItem('userId'),
                unitPrice : unitPrice,
                imageUrl : image,
                qty : count
            }),
        }).
        then(
            alert("장바구니에 담기 성공!")
        )
    }

    const hanlePutOrderList = () => {

        fetch(`/order-service/${localStorage.getItem('userId')}/orders`,{
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                productId: productId,
                qty : count,
                unitPrice : unitPrice
            }),
        }).
        then(
            alert("주문 성공!")
        )
    }

    
    return(
        <div className="pro-details-quality">
            <div className="cart-plus-minus">
                <button className="dec qtybutton" onClick={()=>handleCountDec()}>-</button>
                <input className="cart-plus-minus-box" type="text" readonly="" value={count}/>
                <button className="inc qtybutton" onClick={()=>handleCountAdd()}>+</button>
            </div>
            
            {localStorage.getItem("token") ? 
                <Fragment>
                    <div className="pro-details-cart btn-hover">
                        <button onClick={()=> hanlePutCartList()}> Add To Cart </button>
                    </div>
                    <div className="pro-details-cart btn-hover">
                        {/* <Link to={`/buy/${id}`}>Buy Now</Link> */}
                        <button onClick={()=>linktoBuy(id)}>Buy Now</button>
                        {/*<button onClick={()=> hanlePutOrderList()}> Buy Now </button>*/}
                    </div>
                </Fragment> : 
                <Fragment>
                    <div className="pro-details-cart btn-hover">
                        <Link to="/login">Add To Cart</Link>
                    </div>
                    <div className="pro-details-cart btn-hover ml-0">
                        <Link to="/login">Buy Now</Link>
                    </div>
                </Fragment>
            }
        </div>
    );
}