// import Header from '../../layout/Header';
// import Footer from '../../layout/Footer';
// import Bread from '../../elements/ui/Bread';
// import RegisterForm from '../../elements/widgets/Form/Register';
// import { Fragment } from 'react';
import { useState, useEffect, Fragment } from "react";
import { useHistory } from "react-router";
import { Link } from "react-router-dom";
import { useParams } from "react-router";
import Brand from "../brand/Brand";

export default function JobsDetailForm({data}) {

  const gogo = useHistory();
  const [loading, setLoading] = useState(true);
  const [ values, setValues ] = useState();
  const {jobsNo} = useParams();

  useEffect(()=>{
    fetch(`/user-service/users/${localStorage.getItem('userId')}`)
    .then(res => {
      return res.json();
    })
    .then(data => {
        console.log(data);
        setValues(data);
        setLoading(false);
    })
  },[]);

  const apply = () => {

    fetch(`/user-service/users/apply`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        userId: values.userId,
        applyName: values.name,
        applyEmail: values.email,
        jobsNo: jobsNo,
        applyContact: values.phoneNum
      }),
    }).
      then(
        alert("success"),
        gogo.push('/')
        //window.location.href = '/'

      )
  }

  return (
    <div className="container-scroller">
      <div className="container-fluid page-body-wrapper full-page-wrapper">
        <div className="content-wrapper d-flex align-items-center auth px-0">
          <div className="row w-100 mx-0">
            <div className="col-lg-4 mx-auto">
              <div className="auth-form-light text-left py-5 px-4 px-sm-5">
                <Brand />
                <h4>{data.jobsTitle}</h4>
                <form className="pt-3" >
                  <div className="form-group">
                    {data.jobsContext}
                  </div>
                  <div className="form-group">
                    ?????? ?????? : {data.employType}
                  </div>
                  <div className="form-group">
                    ?????? ?????? : {data.jobType}
                  </div>
                  <div className="form-group">
                    ?????? ?????? : {data.jobQualify}
                  </div>
                  <div className="form-group">
                    ???????????? : {data.recruitNum}???
                  </div>
                  <div className="form-group">
                    ???????????? : {data.favoriteLang}
                  </div>
                  <div className="form-group">
                    ???????????? : {data.jobLocation}
                  </div>
                  <div className="form-group">
                    ?????? ?????? : {data.workDetail}
                  </div>
                  {data.applyStart && data.applyEnd ? 
                  <div className="form-group">
                    ?????? ?????? : {(data.applyStart).substr(0,10)} ~ {(data.applyEnd).substr(0,10)}
                  </div> : ''}

                  {localStorage.getItem('empNo') ? 
                  <Fragment>
                  <div className="mt-3">
                    <button type="button" className="btn btn-block btn-primary btn-lg font-weight-medium auth-form-btn">????????????</button>
                  </div>
                  <div className="mt-3">
                    <button type="button" className="btn btn-block btn-primary btn-lg font-weight-medium auth-form-btn">????????????</button>
                  </div>
                  <div className="mt-3">
                    <button type="button" className="btn btn-block btn-primary btn-lg font-weight-medium auth-form-btn">????????????</button>
                  </div></Fragment> : ''}

                  {localStorage.getItem('userId') ? 
                  <Fragment>
                  <div className="mt-3">
                    <button type="button" className="btn btn-block btn-primary btn-lg font-weight-medium auth-form-btn" onClick={apply}>????????????</button>
                  </div></Fragment> : ''}
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}