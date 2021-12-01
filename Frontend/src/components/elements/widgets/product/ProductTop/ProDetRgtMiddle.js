import { Fragment, useState, useEffect } from "react";
import AddBuyAndCart from './AddBuyAndCart';

export default function ProDetRgtMiddle({productId, productName, qty, unitPrice, stock, image, writer , id}) {

    // const { id } = useParams();

    const [ varData, setVardata ] = useState([]);
    const [ color, setColor] = useState("");
    const [size, setSize] = useState("");

    var process = require('../../../../../db/myProcess.json');
    
    return (
        <Fragment>
            <AddBuyAndCart
                productId = {productId}
                productName = {productName}
                unitPrice = {unitPrice}
                stock = {stock}
                image = {image}
                qty = {qty}
                writer = {writer}
                id = {id}
            /> 
        </Fragment>
    
    );
}