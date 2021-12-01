import { useState, useEffect, useRedf } from "react";
import { useHistory } from "react-router";
import 'react-confirm-alert/src/react-confirm-alert.css';
import Brand from "../brand/Brand";

export default function EditUser() {

  const [address, setAddress] = useState(''); // 주소
  const [addressDetail, setAddressDetail] = useState(''); // 상세주소
  const [isOpenPost, setIsOpenPost] = useState(false);
  const [loading, setLoading] = useState(true);

  const gogo = useHistory();

  const [usersDatas, setUsersDatas] = useState([]);

  const [values, setValues] = useState({
    userId: '',
    email: '',
    password: '',
    confirmPassword: '',
    phoneNum: '',
    name: '',
    address: '',
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

  useEffect(()=>{
    fetch(`/user-service/users/${localStorage.getItem('userId')}`)
    .then(res => {
        return res.json();
    })
    .then(data => {
        setValues(data);
        setLoading(false);
    });
  },[]);

  const isUserId = userId => {
    const userIdRegex = /^[a-z0-9_!@$%^&*-+=?"]{1,20}$/
    return userIdRegex.test(userId);
  }

  const isEmail = email => {
    const emailRegex = /^(([^<>()\].,;:\s@"]+(\.[^<>()\].,;:\s@"]+)*)|(".+"))@(([^<>()¥[\].,;:\s@"]+\.)+[^<>()[\].,;:\s@"]{2,})$/i;

    return emailRegex.test(email);
  };

  const isPwd = pass => {
    const pwdRegex = /^.*(?=.{6,40})(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@$!%*#?&-]).*$/;
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
    if (!isPhone(values.phoneNum)) phoneError = "휴대폰 형식이 아닙니다.";

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
    const valid = onTextCheck();
    if (!valid) {
      console.error("retry");
      alert("정확한 정보를 입력해 주세요");
    }
    else {
      fetch(`/user-service/users`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          userId: values.userId,
          password: values.password,
          name: values.name,
          email: values.email,
          phoneNum: values.phoneNum,
          address: values.address
        }),
      }).
        then(
          alert("회원정보가 수정되었습니다."),
          gogo.push('/')
          //window.location.href = '/'
        )
    }
  }

  const deleteUser = (e) => {
    // e.preventDefault();
    fetch(`/user-service/users`, {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        userId: localStorage.getItem('userId'), // 토큰에서 가지고 있어야 함. 유저 조회 기능 넣어서 가져온 뒤 비밀번호 비교 후에 짜야 할 듯
        password: values.password
      }),
    }).
      then(
        alert("탈퇴 성공!"),
        localStorage.clear(),
        gogo.push('/')
      )
  }

  const useConfirm = (message = null, onConfirm, onCancel, deleteUser) => {
    if (!onConfirm || typeof onConfirm !== "function") {
      return;
    }
    if (onCancel && typeof onCancel !== "function") {
      return;
    }

    const confirmAction = () => {
      if (window.confirm(message)) {
        onConfirm();
        deleteUser();
      } else {
        onCancel();
      }
    };

    return confirmAction;
  };

  const deleteConfirm = () => 1;
  const cancelConfirm = () => 0;
  const confirmDelete = useConfirm(
    "삭제하시겠습니까?",
    deleteConfirm,
    cancelConfirm,
    deleteUser
  );

  if (loading) return <div class="spinner-border text-primary" role="status"></div>;
  return (
    <div class="container-scroller">
      <div class="container-fluid page-body-wrapper full-page-wrapper">
        <div class="content-wrapper d-flex align-items-center auth px-0">
          <div class="row w-100 mx-0">
            <div class="col-lg-4 mx-auto">
              <div class="auth-form-light text-left py-5 px-4 px-sm-5">
                <Brand />
                <h4>회원정보 수정</h4>
                <form class="pt-3" onSubmit={handlePutUserLists}>
                  <div class="form-group">
                    <input type="text" class="form-control form-control-lg" id="exampleInputUsername1" placeholder="이메일" readOnly
                      name="email"
                      value={values.email}
                      onChange={handleChangeForm} />
                  </div>
                  <div class="form-group">
                    <input type="text" class="form-control form-control-lg" id="exampleInputEmail1" placeholder="이름"
                      name="name"
                      value={values.name}
                      onChange={handleChangeForm} />
                  </div>
                  <div class="form-group">
                    <input type="password" class="form-control form-control-lg" id="exampleInputEmail1" placeholder="비밀번호"
                      name="password"
                      value={values.password}
                      onChange={handleChangeForm} />
                  </div>
                  <div class="form-group">
                    <input type="password" class="form-control form-control-lg" id="exampleInputEmail1" placeholder="비밀번호 확인"
                      name="confirmPassword"
                      value={values.confirmPassword}
                      onChange={handleChangeForm} />
                  </div>
                  <div class="form-group">
                    <input type="text" class="form-control form-control-lg" id="exampleInputUsername1" placeholder="휴대폰 번호" name="phone"
                      value={values.phoneNum}
                      onChange={handleChangeForm} />
                  </div>
                  <div class="form-group">
                    <input type="text" class="form-control form-control-lg" id="exampleInputUsername1" placeholder="주소" name="address"
                      value={values.address}
                      onChange={handleChangeForm} />
                  </div>
                  <div class="mt-3">
                    <button type="submit" class="btn btn-block btn-primary btn-lg font-weight-medium auth-form-btn">수정하기</button>
                  </div>
                  <div class="mt-3">
                    <button type="button" class="btn btn-block btn-danger btn-lg font-weight-medium auth-form-btn" onClick={confirmDelete}>탈퇴하기</button>
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