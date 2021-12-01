// import Header from '../../layout/Header';
// import Footer from '../../layout/Footer';
// import Bread from '../../elements/ui/Bread';
// import RegisterForm from '../../elements/widgets/Form/Register';
// import { Fragment } from 'react';
import { useState, useEffect } from "react";
import { useHistory } from "react-router";
import { Link } from "react-router-dom";
import Brand from "../brand/Brand";
import moment from "moment";

export default function CreateJobsForm() {

  const gogo = useHistory();

  const [values, setValues] = useState({
    jobsNo: '',
    corpNo: '',
    empNo: '',
    jobsTitle: '',
    jobsContext: '',
    recruitNum: '',
    favoriteLang: '',
    jobLocation: '',
    jobType: '',
    jobQualify: '',
    applyStart: '',
    applyEnd: '',
    employType: '',
    workDetail: '',
    intv1Start: '',
    intv1End: '',
    intv2Start: '',
    intv2End: '',

    writtenMultiple: '',
    intv1Multiple: '',
    intv2Multiple: '',
    intv2Pass: '',
  })

  const [guideTxts, setGuideTxts] = useState({
    userGuide: '최대 20자 까지 가능합니다.',
    emailGuide: '이메일 형식에 맞게 작성해 주세요.',
    pwdGuide: '숫자와 문자를 조합해서 최소 8글자는 입력해 주세요.',
    confirmPwdGuide: '한번더 입력해 주세요.',
    nameGuide: '',
    phoneGuide: '. 을 입력하지 말아 주세요.'
  });

  const [error, setError] = useState({
    userIdError: '',
    emailError: '',
    pwdError: '',
    confirmPwd: '',
    nameError: '',
    phoneError: ''
  })

  const isUserId = userId => {
    const userIdRegex = /^[a-z0-9_!@$%^&*-+=?"]{1,20}$/
    return userIdRegex.test(userId);
  }

  const isEmail = email => {
    const emailRegex = /^(([^<>()\].,;:\s@"]+(\.[^<>()\].,;:\s@"]+)*)|(".+"))@(([^<>()¥[\].,;:\s@"]+\.)+[^<>()[\].,;:\s@"]{2,})$/i;

    return emailRegex.test(email);
  };

  const isPwd = pass => {
    const pwdRegex = /^.*(?=.{6,20})(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@$!%*#?&]).*$/;

    return pwdRegex.test(pass);
  }

  const isPhone = phone => {
    const phoneRegex = /^[0-9\b -]{0,13}$/;
    return phoneRegex.test(phone)
  }

  const confirmPassword = (pass, confirmPass) => {
    return pass === confirmPass
  }

  const onTextCheck = () => {
    let userIdError = "";
    let emailError = "";
    let pwdError = "";
    let confirmPwd = "";
    let nameError = "";
    let phoneError = "";


    if (!isUserId(values.userId)) userIdError = "아이디 형식을 확인 해 주세요.( 한글 불가 )";
    if (!isEmail(values.email)) emailError = "email 형식이 아닙니다.";
    if (!isPwd(values.password)) pwdError = "비밀번호 조건을 만족 할 수 없습니다.";
    if (!confirmPassword(values.password, values.confirmPassword)) confirmPwd = "비밀번호가 일치하지 않습니다.";
    if (values.userId === values.password) pwdError = "아이디를 비밀번호로 사용 할 수 없습니다.";
    if (!isPhone(values.phone)) phoneError = "휴대폰 형식이 아닙니다.";
    if (values.name.length === 0) nameError = "이름을 입력해주세요.";
    setError({
      userIdError, emailError, pwdError, confirmPwd, nameError, phoneError
    })

    if (userIdError || emailError || pwdError || confirmPwd || nameError || phoneError) return false;
    return true;
  }

  const handleChangeForm = (e) => {
    setValues({
      ...values,
      [e.target.name]: e.target.value
    });
  }

  const handlePutUserLists = (e) => {

    e.preventDefault();

    fetch(`/job-service/jobs`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        corpNo: localStorage.getItem('corpNo'),
        empNo: localStorage.getItem('empNo'),
        jobsTitle: values.jobsTitle,
        jobsContext: values.jobsContext,
        recruitNum: values.recruitNum,
        favoriteLang: values.favoriteLang,
        jobLocation: values.jobLocation,
        jobType: values.jobType,
        employType: values.employType,
        jobQualify: values.jobQualify,
        workDetail: values.workDetail,
        applyStart: values.applyStart,
        applyEnd: values.applyEnd,
        intv1Start: values.intv1Start,
        intv1End: values.intv1End,
        intv2Start: values.intv2Start,
        intv2End: values.intv2End,
        writtenMultiple: values.writtenMultiple,
        intv1Multiple: values.intv1Multiple,
        intv2Multiple: values.intv2Multiple
      }),
    }).
      then(
        alert("공고 생성 완료"),
        gogo.push('/')
        //window.location.href = '/'
      )
    // }
  }
  return (
    <div className="container-scroller">
      <div className="container-fluid page-body-wrapper full-page-wrapper">
        <div className="content-wrapper d-flex align-items-center auth px-0">
          <div className="col-12 grid-margin">
            <div className="card">
              <div style={{ margin: "3rem 3rem 0" }}><Brand /></div>
              <div className="card-body" style={{ marginBottom: "3rem" }}>
                <h2 className="creat-job-title text-uppercase">Job Creation Form</h2>
                <hr style={{ marginBottom: "3.5rem" }} />
                <form style={{ marginLeft: "3%" }} className="creat-job-font" onSubmit={handlePutUserLists}>
                  <div className="row">
                    <div className="col-md-6">
                      <div className="form-group row">
                        <label className="col-sm-2 col-form-label">공고제목</label>
                        <div className="col-sm-9">
                          <input type="text" className="form-control form-control-lg" id="exampleInputUsername1"
                            name="jobsTitle"
                            value={values.jobsTitle}
                            onChange={handleChangeForm}
                            placeholder="공고명" />
                        </div>
                      </div>
                    </div>
                    <div className="col-md-6">
                      <div className="form-group row">
                        <label className="col-sm-2 col-form-label">선호언어</label>
                        <div className="col-sm-9">
                          <input className="form-control form-control-lg" id="exampleInputUsername1"
                            name="favoriteLang"
                            value={values.favoriteLang}
                            onChange={handleChangeForm}
                            placeholder="ex ) JAVA C++" />
                        </div>
                      </div>
                    </div>
                  </div>
                  <div className="row">
                    <div className="col-md-6">
                      <div className="form-group row">
                        <label className="col-sm-2 col-form-label">공고설명</label>
                        <div className="col-sm-9">
                          <textarea cols="30" style={{ height: "150px" }} className="form-control form-control-lg" id="exampleInputUsername1"
                            name="jobsContext"
                            value={values.jobsContext}
                            onChange={handleChangeForm}
                            placeholder="공고 부가 설명"></textarea>
                        </div>
                      </div>
                    </div>
                    <div className="col-md-6">
                      <div className="form-group row">
                        <label className="col-sm-2 col-form-label">업무내용</label>
                        <div className="col-sm-9">
                          <textarea cols="30" style={{ height: "150px" }} className="form-control form-control-lg" id="exampleInputUsername1"
                            name="workDetail"
                            value={values.workDetail}
                            onChange={handleChangeForm}
                            placeholder="ex ) 본인이 맡은 프로덕트의 당면한 문제를 파악하고, 사용자에게 더 나은 가치를 주기 위한 여러가지 솔루션을 제안합니다.&#13;&#10;- 문제에 대한 다양한 가설을 수립하고 솔루션을 제시해요." ></textarea>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div className="row">
                    <div className="col-md-6">
                      <div className="form-group row">
                        <label className="col-sm-2 col-form-label">고용유형</label>
                        <div className="col-sm-9">
                          <input className="form-control form-control-lg" id="exampleInputUsername1"
                            name="jobType"
                            value={values.jobType}
                            onChange={handleChangeForm}
                            placeholder="ex ) 정규직/인턴/무기 계약직" />
                        </div>
                      </div>
                    </div>
                    <div className="col-md-6">
                      <div className="form-group row">
                        <label className="col-sm-2 col-form-label">지원자격</label>
                        <div className="col-sm-9">
                          <input className="form-control form-control-lg" id="exampleInputUsername1"
                            name="jobQualify"
                            value={values.jobQualify}
                            onChange={handleChangeForm}
                            placeholder="ex ) 해외여행에 결격사유가 없으신 분" />
                        </div>
                      </div>
                    </div>
                  </div>
                  <div className="row">
                    <div className="col-md-6">
                      <div className="form-group row">
                        <label className="col-sm-2 col-form-label">고용형태</label>
                        <div className="col-sm-9">
                          <input className="form-control form-control-lg" id="exampleInputUsername1"
                            name="employType"
                            value={values.employType}
                            onChange={handleChangeForm}
                            placeholder="ex ) 신입/경력/무관" />
                        </div>
                      </div>
                    </div>
                    <div className="col-md-6">
                      <div className="form-group row">
                        <label className="col-sm-2 col-form-label">근무지역</label>
                        <div className="col-sm-9">
                          <input className="form-control form-control-lg" id="exampleInputUsername1"
                            name="jobLocation"
                            value={values.jobLocation}
                            onChange={handleChangeForm}
                            placeholder="ex ) 서울/세종" />
                        </div>
                      </div>
                    </div>
                  </div>
                  <hr style={{ width: "97%", marginTop: "2.5rem" }} />
                  <p className="card-inmidle text-uppercase">
                    Date setting Form
                  </p>
                  <div className="row">
                    <div className="col-md-6">
                      <div className="form-group row">
                        <label className="col-sm-3 col-form-label">지원 시작일</label>
                        <div className="col-sm-8">
                          <input className="form-control form-control-lg" id="exampleInputUsername1"
                            name="applyStart"
                            value={values.applyStart}
                            onChange={handleChangeForm}
                            placeholder="YYYY-MM-DD hh:mm" />
                        </div>
                      </div>
                    </div>
                    <div className="col-md-6">
                      <div className="form-group row">
                        <label className="col-sm-3 col-form-label">지원 마감일</label>
                        <div className="col-sm-8">
                          <input className="form-control form-control-lg" id="exampleInputUsername1"
                            name="applyEnd"
                            value={values.applyEnd}
                            onChange={handleChangeForm}
                            placeholder="YYYY-MM-DD hh:mm" />
                        </div>
                      </div>
                    </div>
                  </div>
                  <div className="row">
                    <div className="col-md-6">
                      <div className="form-group row">
                        <label className="col-sm-3 col-form-label">1차 면접 시작일</label>
                        <div className="col-sm-8">
                          <input className="form-control form-control-lg" id="exampleInputUsername1"
                            name="intv1Start"
                            value={values.intv1Start}
                            onChange={handleChangeForm}
                            placeholder="YYYY-MM-DD hh:mm" />
                        </div>
                      </div>
                    </div>
                    <div className="col-md-6">
                      <div className="form-group row">
                        <label className="col-sm-3 col-form-label">1차 면접 마감일</label>
                        <div className="col-sm-8">
                          <input className="form-control form-control-lg" id="exampleInputUsername1"
                            name="intv1End"
                            value={values.intv1End}
                            onChange={handleChangeForm}
                            placeholder="YYYY-MM-DD hh:mm" />
                        </div>
                      </div>
                    </div>
                  </div>
                  <div className="row">
                    <div className="col-md-6">
                      <div className="form-group row">
                        <label className="col-sm-3 col-form-label">2차 면접 시작일</label>
                        <div className="col-sm-8">
                          <input className="form-control form-control-lg" id="exampleInputUsername1"
                            name="intv2Start"
                            value={values.intv2Start}
                            onChange={handleChangeForm}
                            placeholder="YYYY-MM-DD hh:mm" />
                        </div>
                      </div>
                    </div>
                    <div className="col-md-6">
                      <div className="form-group row">
                        <label className="col-sm-3 col-form-label">2차 면접 마감일</label>
                        <div className="col-sm-8">
                          <input className="form-control form-control-lg" id="exampleInputUsername1"
                            name="intv2End"
                            value={values.intv2End}
                            onChange={handleChangeForm}
                            placeholder="YYYY-MM-DD hh:mm" />
                        </div>
                      </div>
                    </div>
                  </div>
                  <div className="row">
                    <div className="col-md-6">
                      <div className="form-group row">
                        <label className="col-sm-3 col-form-label">채용 인원</label>
                        <div className="col-sm-8">
                          <input className="form-control form-control-lg" id="exampleInputUsername1"
                            name="recruitNum"
                            value={values.recruitNum}
                            onChange={handleChangeForm}
                            placeholder="숫자만 입력해 주세요" />
                        </div>
                      </div>
                    </div>
                    <div className="col-md-6">
                      <div className="form-group row">
                        <label className="col-sm-3 col-form-label">필기 합격 배수</label>
                        <div className="col-sm-8">
                          <input type="text" className="form-control form-control-lg" id="exampleInputEmail1"
                            name="writtenMultiple"
                            value={values.writtenMultiple}
                            onChange={handleChangeForm}
                            placeholder="숫자만 입력해 주세요" />
                        </div>
                      </div>
                    </div>
                  </div>
                  <div className="row">
                    <div className="col-md-6">
                      <div className="form-group row">
                        <label className="col-sm-3 col-form-label">1차 면접 합격 배수</label>
                        <div className="col-sm-8">
                          <input type="text" className="form-control form-control-lg" id="exampleInputEmail1"
                            name="intv1Multiple"
                            value={values.intv1Multiple}
                            onChange={handleChangeForm}
                            placeholder="숫자만 입력해 주세요" />
                        </div>
                      </div>
                    </div>
                    <div className="col-md-6">
                      <div className="form-group row">
                        <label className="col-sm-3 col-form-label">2차 면접 합격 배수</label>
                        <div className="col-sm-8">
                          <input type="text" className="form-control form-control-lg" id="exampleInputEmail1"
                            name="intv2Multiple"
                            value={values.intv2Multiple}
                            onChange={handleChangeForm}
                            placeholder="숫자만 입력해 주세요" />
                        </div>
                      </div>
                    </div>
                  </div>
                  <div className="mt-3">
                    <button type="submit" className="btn btn-block btn-primary btn-lg font-weight-medium auth-form-btn">등록하기</button>
                  </div>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}