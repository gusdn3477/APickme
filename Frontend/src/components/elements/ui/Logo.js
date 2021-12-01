import { Link } from "react-router-dom";

export default function Logo(){

    return(
        <div className="col-xl-2 col-lg-2 col-md-6 col-4">

        <div className="logo">
            <Link to="/"><div style={{fontSize:"25px", fontWeight : "bold", color : "black"}}>3조 책방</div></Link>
            {/*<a href="/"><img alt="" src="assets/img/logo/logo.png"/></a>*/}
            </div>
        </div>
    );
}