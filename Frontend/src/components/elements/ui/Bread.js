import {Link} from 'react-router-dom';


export default function Bread({breadId, breadName, breadUrl}){
    return(

        <div className="breadcrumb-area pt-35 pb-35 bg-gray-3">
        <div classNameName="container">
            <div className="breadcrumb-content text-center">
                <span>
                    <span><Link to="/" aria-current="page" className="active" >Home</Link><span>/</span></span>
                    <span to={breadUrl}>{breadName}</span>
                </span>
            </div>
        </div>
    </div>
    );
}


