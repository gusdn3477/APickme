import { Fragment } from "react";
import { Link } from "react-router-dom";
export default function ProDetRgtBottom() {

    return(
        <Fragment>
            <div className="pro-details-meta">
                <span>Categories :</span>
                <ul>
                    <li><Link to ="/shop-grid-standard">Book</Link></li>
                    
                </ul>
            </div>
            <div className="pro-details-meta">
                <span>Tags :</span>
                <ul>
                    <li><a href="/shop-grid-standard">Book</a></li>
                </ul>
            </div>
        </Fragment>
    );
}

