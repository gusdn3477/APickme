// import Header from '../../layout/Header';
// import Footer from '../../layout/Footer';
// import Bread from '../../elements/ui/Bread';
// import RegisterForm from '../../elements/widgets/Form/Register';
// import { Fragment } from 'react';
import { useState, useEffect } from "react";
import { useHistory } from "react-router";
import { useParams } from "react-router";
import Brand from "../brand/Brand";

export default function JobsApplyForm() {
  const gogo = useHistory();
  const [loading, setLoading] = useState(true);
  const [values, setValues] = useState();
  const [portfolio, setPortfolio] = useState();
  const { jobsNo } = useParams();

  useEffect(() => {
    fetch(`/user-service/users/${localStorage.getItem("userId")}`)
      .then((res) => {
        return res.json();
      })
      .then((data) => {
        console.log(data);
        setValues(data);
        setLoading(false);
      });
  }, []);

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
        applyContact: values.phoneNum,
        portfolio: values.portfolio,
      }),
    }).then(
      alert("지원 완료되었습니다."),
      gogo.push("/")
      //window.location.href = '/'
    );
  };

  const handleChangeForm = (e) => {
    setValues({
      ...values,
      [e.target.name]: e.target.value,
    });
  };

  const handleFileForm = (e) => {
    setValues({
      ...values,
      [e.target.name]: e.target.value,
    });
  };

  if (loading)
    return <div class="spinner-border text-primary" role="status"></div>;
  return (
    <div className="container-scroller">
      <div className="container-fluid page-body-wrapper full-page-wrapper">
        <div className="content-wrapper d-flex align-items-center auth px-0">
          <div className="row w-100 mx-0">
            <div className="col-lg-4 mx-auto">
              <div className="auth-form-light text-left py-5 px-4 px-sm-5">
                <Brand />
                <h4>지원 페이지</h4>
                <form className="pt-3">
                  <div class="form-group">
                    <input
                      type="text"
                      class="form-control form-control-lg"
                      id="exampleInputUsername1"
                      placeholder="이메일"
                      readOnly
                      name="email"
                      value={values.email}
                      onChange={handleChangeForm}
                    />
                  </div>
                  <div class="form-group">
                    <input
                      type="text"
                      class="form-control form-control-lg"
                      id="exampleInputEmail1"
                      placeholder="이름"
                      readOnly
                      name="name"
                      value={values.name}
                      onChange={handleChangeForm}
                    />
                  </div>
                  <div class="form-group">
                    <input
                      type="text"
                      class="form-control form-control-lg"
                      id="exampleInputUsername1"
                      placeholder="휴대폰 번호"
                      name="phoneNum"
                      value={values.phoneNum}
                      onChange={handleChangeForm}
                    />
                  </div>
                  <div class="form-group">
                    <input
                      type="text"
                      class="form-control form-control-lg"
                      id="exampleInputUsername1"
                      placeholder="주소"
                      name="address"
                      value={values.address}
                      onChange={handleChangeForm}
                    />
                  </div>
                  {/* <div class="form-group">
                    <input
                      type="file"
                      class="form-control form-control-lg"
                      id="exampleInputUsername1"
                      placeholder="포트폴리오"
                      name="portfolio"
                      value={values.portfolio}
                      onChange={handleFileForm}
                    />
                  </div> */}
                  <div className="mt-3">
                    <button
                      type="button"
                      className="btn btn-block btn-primary btn-lg font-weight-medium auth-form-btn"
                      onClick={apply}
                    >
                      지원하기
                    </button>
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
