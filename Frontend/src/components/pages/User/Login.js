import { useState, useEffect } from "react";
import { Redirect, useHistory } from "react-router";
import { Link } from "react-router-dom";
import Brand from "../../elements/widgets/brand/Brand";
import axios from 'axios';
import KaKaoLogin from 'react-kakao-login';

// 카카오 로그인 연습
// import { KAKAO_AUTH_URL } from "../../../utilities/Oauth";

// 둘다 로그인
// 이거 공용입니다. 헷갈리지 마세요!
export default function UserLogin() {

  const gogo = useHistory();
  const [values, setValues] = useState({
    userEmail: '',
    userPassword: '',
    hrEmail: '',
    hrPassword: ''
  })

  const [guideTxts, setGuideTxts] = useState({
    emailGuide: '이메일 형식에 맞게 작성해 주세요.',
    pwdGuide: '숫자와 문자를 조합해서 최소 8글자는 입력해 주세요.'
  });

  const [guideTxts2, setGuideTxts2] = useState({
    emailGuide: '이메일 형식에 맞게 작성해 주세요.',
    pwdGuide: '숫자와 문자를 조합해서 최소 8글자는 입력해 주세요.'
  });

  const [error, setError] = useState({
    emailError: '',
    pwdError: ''
  })

  const [error2, setError2] = useState({
    emailError: '',
    pwdError: ''
  })

  const isEmail = email => {
    const emailRegex = /^(([^<>()\].,;:\s@"]+(\.[^<>()\].,;:\s@"]+)*)|(".+"))@(([^<>()¥[\].,;:\s@"]+\.)+[^<>()[\].,;:\s@"]{2,})$/i;
    return emailRegex.test(email);
  };

  const isPwd = pass => {
    const pwdRegex = /^.*(?=.{6,40})(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@$!%*#?&-]).*$/;
    return pwdRegex.test(pass);
  }

  const userOnTextCheck = () => {
    let emailError = "";
    let pwdError = "";

    if (!isEmail(values.userEmail)) emailError = "email 형식이 아닙니다.";
    if (!isPwd(values.userPassword)) pwdError = "비밀번호 조건을 만족 할 수 없습니다.";

    setError({
      emailError, pwdError
    })

    if (emailError || pwdError) return false;
    return true;
  }

  const hrOnTextCheck = () => {
    let emailError = "";
    let pwdError = "";

    if (!isEmail(values.hrEmail)) emailError = "email 형식이 아닙니다.";
    if (!isPwd(values.hrPassword)) pwdError = "비밀번호 조건을 만족 할 수 없습니다.";

    setError2({
      emailError, pwdError
    })

    if (emailError || pwdError) return false;
    return true;
  }

  const handleChangeForm = (e) => {
    setValues({
      ...values,
      [e.target.name]: e.target.value
    });
  }

  // 유저 로그인
  const userLogin = (e) => {
    e.preventDefault();

    const valid = userOnTextCheck();
    if (!valid) {
      alert("입력한 정보를 확인해 주세요")
    }

    else {
      fetch(`/user-service/login`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify({
          email: values.userEmail,
          password: values.userPassword
        })
      }) // res.json() 해 줘야 되는지 생각..
        .then((res) => {
          if (res.headers.get('token')) {
            localStorage.setItem("token", res.headers.get('token'));
            localStorage.setItem("userId", res.headers.get('userId'));
            // localStorage.setItem("email", values.email);
            gogo.push("/");
          }
          else {
            alert("로그인 정보를 확인하세요.");
          }
        })
    }
  }

  // 인사담당자 로그인
  const hrLogin = (e) => {
    e.preventDefault();

    const valid = hrOnTextCheck();
    if (!valid) {
      alert("입력한 정보를 확인해 주세요")
    }

    else {
      fetch(`/hr-service/login`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify({
          email: values.hrEmail,
          pwd: values.hrPassword
        })
      }) // res.json() 해 줘야 되는지 생각..
        .then((res) => {
          if (res.headers.get('token')) {
            localStorage.setItem("token", res.headers.get('token'));
            localStorage.setItem("empNo", res.headers.get('empNo'));
            localStorage.setItem("corpNo", res.headers.get('corpNo'));
            gogo.push('/');
          }
          else {
            alert("로그인 정보를 확인하세요.");
          }
        })
    }
  }

  if (!localStorage.getItem("token")) {
    return (
      <div class="container-scroller">
        <div class="container-fluid page-body-wrapper full-page-wrapper">
          <div class="content-wrapper d-flex align-items-center auth px-0">
            <div class="row w-100 mx-0">
              <div class="col-lg-4 mx-auto">
                <div class="auth-form-light text-left py-5 px-4 px-sm-5">
                  <Brand />
                  {/* <h6 class="font-weight-light">Sign in to continue.</h6> */}
                  <nav>
                    <div class="nav nav-tabs" id="nav-tab" role="tablist">
                      <button class="nav-link active" id="nav-home-tab" data-bs-toggle="tab" data-bs-target="#nav-home" type="button" role="tab" aria-controls="nav-home" aria-selected="true">사용자 로그인</button>
                      <button class="nav-link" id="nav-profile-tab" data-bs-toggle="tab" data-bs-target="#nav-profile" type="button" role="tab" aria-controls="nav-profile" aria-selected="false">관리자 로그인</button>
                    </div>
                  </nav>
                  <div class="tab-content" id="nav-tabContent">
                    <div class="tab-pane fade show active" id="nav-home" role="tabpanel" aria-labelledby="nav-home-tab">
                      <form class="pt-3" onSubmit={userLogin}>
                        <div class="form-group">
                          <input type="email" class="form-control form-control-lg" id="exampleInputEmail1" placeholder="Email" name="userEmail" value={values.userEmail} onChange={handleChangeForm} />
                        </div>
                        {
                          error.emailError
                            ?
                            <div style={{ color: "red", fontSize: "12px", margin: '-5px 0 10px 15px' }}>{error.emailError}</div>
                            :
                            <div style={{ color: "gray", fontSize: "12px", margin: '-5px 0 10px 15px' }}>{guideTxts.userGuide}</div>
                        }
                        <div class="form-group">
                          <input type="password" class="form-control form-control-lg" id="exampleInputPassword1" placeholder="Password"
                            name="userPassword"
                            value={values.userPassword}
                            onChange={handleChangeForm} />
                        </div>
                        {
                          error.pwdError
                            ?
                            <div style={{ color: "red", fontSize: "12px", margin: '-5px 0 10px 15px' }}>{error.pwdError}</div>
                            :
                            <div style={{ color: "gray", fontSize: "12px", margin: '-5px 0 10px 15px' }}>{guideTxts.pwdGuide}</div>
                        }
                        <div class="mt-3">
                          <button type="submit" class="btn btn-block btn-primary btn-lg font-weight-medium auth-form-btn">로그인</button>
                        </div>
                        <div class="my-2 d-flex justify-content-between align-items-center">
                          <div class="form-check">
                           {/*  <label class="form-check-label text-muted">
                              <input type="checkbox" class="form-check-input" />
                              로그인 기억하기
                            </label> */}
                          </div>
                          <Link to="/user/findpwd" class="auth-link text-black">비밀번호를 잊으셨나요?</Link>
                        </div>
                        {/* <div class="mb-2">
                        <button type="button" class="btn btn-block btn-facebook auth-form-btn">
                          <i class="ti-facebook mr-2"></i>페이스북 계정으로 시작하기
                        </button>
                      </div> */}
                        <div class="text-center mt-4 font-weight-light">
                          지원자 계정으로 가입하고 싶으시다면? <Link to="/user/register" class="text-primary">Click</Link>
                        </div>
                      </form>
                    </div>


                    {/* 여기부터 인사담당자 로그인 창 */}
                    <div class="tab-pane fade" id="nav-profile" role="tabpanel" aria-labelledby="nav-profile-tab">
                      <form class="pt-3" onSubmit={hrLogin}>
                        <div class="form-group">
                          <input type="email" class="form-control form-control-lg" id="exampleInputEmail1" placeholder="Email" name="hrEmail" value={values.hrEmail} onChange={handleChangeForm} />
                        </div>
                        {
                          error.emailError
                            ?
                            <div style={{ color: "red", fontSize: "12px", margin: '-5px 0 10px 15px' }}>{error2.emailError}</div>
                            :
                            <div style={{ color: "gray", fontSize: "12px", margin: '-5px 0 10px 15px' }}>{guideTxts2.userGuide}</div>
                        }
                        <div class="form-group">
                          <input type="password" class="form-control form-control-lg" id="exampleInputPassword" placeholder="Password"
                            name="hrPassword"
                            value={values.hrPassword}
                            onChange={handleChangeForm} />
                        </div>
                        {
                          error.pwdError
                            ?
                            <div style={{ color: "red", fontSize: "12px", margin: '-5px 0 10px 15px' }}>{error2.pwdError}</div>
                            :
                            <div style={{ color: "gray", fontSize: "12px", margin: '-5px 0 10px 15px' }}>{guideTxts2.pwdGuide}</div>
                        }
                        <div class="mt-3">
                          <button type="submit" class="btn btn-block btn-primary btn-lg font-weight-medium auth-form-btn">로그인</button>
                        </div>
                        <div class="my-2 d-flex justify-content-between align-items-center">
                          <div class="form-check">
                            {/* <label class="form-check-label text-muted">
                              <input type="checkbox" class="form-check-input" />
                              로그인 기억하기
                            </label> */}
                          </div>
                          <Link to="/hr/findpwd" class="auth-link text-black">비밀번호를 잊으셨나요?</Link>
                        </div>
                        {/* <div class="mb-2">
                        <button type="button" class="btn btn-block btn-facebook auth-form-btn">
                          <i class="ti-facebook mr-2"></i>페이스북 계정으로 시작하기
                        </button>
                      </div> */}
                        <div class="text-center mt-4 font-weight-light">
                          인사담당자 계정으로 가입하고 싶으시다면? <Link to="/hr/register" class="text-primary">Click</Link>
                        </div>
                      </form>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
  else{
    return <Redirect to="/"></Redirect>
  }
}