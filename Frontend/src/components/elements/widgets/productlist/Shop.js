import ProductView from "../product/ProductView";
import React, {useState, useEffect} from 'react';

export default function Shop({categoryName}){
  
    const [sliceNumber , setSliceNumber ] = useState(15);
    const [columnNumber , setColumnNumber ] = useState(4);
    const [onActive, setOnActive] = useState(false);  

    const handleLayout = (sln, coln) => {
        setSliceNumber(sln)
        setColumnNumber(coln)
        setOnActive(!onActive)
    }

    return(
    <div className="col-lg-9 order-1 order-lg-2">

        <div className="shop-top-bar mb-35">
            <div className="select-shoing-wrap">
                <div className="shop-select">
                    <select>
                        <option value="default">Default</option>
                        <option value="priceHighToLow">Price - High to Low</option>
                        <option value="priceLowToHigh">Price - Low to High</option>
                    </select>
                </div>
            </div>
            <div className="shop-tab">
                <button className={ onActive ? "active" : ""} onClick={() => handleLayout(10,6)}><i className="fa fa-th-large"></i></button>
                <button className={ onActive ? "" : "active"} onClick={() => handleLayout(15,4)}><i className="fa fa-th"></i></button>
                {/* <button className=""><i className="fa fa-list-ul"></i></button> */}
            </div>
        </div>

        <div className="shop-bottom-area mt-35">
            <div className="row grid three-column">
                
            <ProductView   
                sliceNumber = {sliceNumber}
                columnNumber = {columnNumber}
                categoryName = {categoryName}
            />

            </div>
        </div>

        <div className="pro-pagination-style text-center mt-30">
            <ul className="mb-0 mt-0">
                <li className="page-item active"><button className="page-link">1</button></li>
                <li className="page-item null"><button className="page-link">2</button></li>
                <li className="page-item null"><button className="page-link">3</button></li>
                <li className="page-item null"><button className="page-link">4</button></li>
                <li className="page-item null"><button className="page-link">5</button></li>
                <li className="page-item null"><button className="page-link">6</button></li>
                <li className="page-item null"><button className="page-link">7</button></li>
                <li className="page-item"><button className="page-link">»</button></li>
                <li className="page-item null"><button className="page-link">10</button></li>
            </ul>
        </div>

    </div>

    );
}