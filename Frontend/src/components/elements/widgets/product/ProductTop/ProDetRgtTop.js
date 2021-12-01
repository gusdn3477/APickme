import { Fragment,useState } from "react";
import Rating from '../../../ui/Rating';

export default function ProDetRgtTop({name,price,writer}) {

    return (
        <Fragment>
            <h2>{name}</h2>
            <div className="product-details-price">
                <span>{price}원</span>
            </div>
            <div className="pro-details-list">
                <span>지은이 : {writer}</span>
            </div>
            <div className="pro-details-rating-wrap">
            </div>
        </Fragment>
    );
}