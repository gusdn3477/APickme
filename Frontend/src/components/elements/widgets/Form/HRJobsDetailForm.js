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
                    채용 형태 : {data.employType}
                  </div>
                  <div className="form-group">
                    고용 형태 : {data.jobType}
                  </div>
                  <div className="form-group">
                    지원 자격 : {data.jobQualify}
                  </div>
                  <div className="form-group">
                    채용인원 : {data.recruitNum}명
                  </div>
                  <div className="form-group">
                    우대언어 : {data.favoriteLang}
                  </div>
                  <div className="form-group">
                    근무지역 : {data.jobLocation}
                  </div>
                  <div className="form-group">
                    상세 내용 : {data.workDetail}
                  </div>
                  {data.applyStart && data.applyEnd ? 
                  <div className="form-group">
                    모집 기간 : {(data.applyStart).substr(0,10)} ~ {(data.applyEnd).substr(0,10)}
                  </div> : ''}

                  {localStorage.getItem('empNo') ? 
                  <Fragment>
                  <div className="mt-3">
                    <button type="button" className="btn btn-block btn-primary btn-lg font-weight-medium auth-form-btn">마감하기</button>
                  </div>
                  <div className="mt-3">
                    <button type="button" className="btn btn-block btn-primary btn-lg font-weight-medium auth-form-btn">수정하기</button>
                  </div>
                  <div className="mt-3">
                    <button type="button" className="btn btn-block btn-primary btn-lg font-weight-medium auth-form-btn">삭제하기</button>
                  </div></Fragment> : ''}

                  {localStorage.getItem('userId') ? 
                  <Fragment>
                  <div className="mt-3">
                    <button type="button" className="btn btn-block btn-primary btn-lg font-weight-medium auth-form-btn" onClick={apply}>지원하기</button>
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