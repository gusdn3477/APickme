import { useEffect, useState } from "react";

export default function Welcome() {

  let today = new Date();
  const [data, setData] = useState();
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if(localStorage.getItem('userId')){
      fetch(`/user-service/users/${localStorage.getItem('userId')}`)
      .then(res => {
        return res.json();
      })
      .then(data => {
        setData(data);
        setLoading(false);
      })
    }

    if(localStorage.getItem('empNo')){
      fetch(`/hr-service/hr/detail/${localStorage.getItem('empNo')}`)
      .then(res => {
        return res.json();
      })
      .then(data => {
        setData(data);
        setLoading(false);
      })
    }
  }, []);

  if (loading) return <div class="main-header pad-top wrapper" id="mainHeader"><h1 class="text-giga">Loading ...</h1> <div class="loader loader-6"/></div>;
  return (
    <div className="col-md-12 grid-margin">
      <div className="row">
        <div className="col-12 col-xl-8 mb-4 mb-xl-0 ">
          <h3 className="font-weight-bold" style={{fontFamily:"Nunito"}}>{data.name}님, 환영합니다!</h3>
          {/* <h6 className="font-weight-normal mb-0">All systems are running smoothly! You have <span className="text-primary">3 unread alerts!(알림 구현시 사용)</span></h6> */}
        </div>
        <div className="col-12 col-xl-4">
          <div className="justify-content-end d-flex">
            <div className="dropdown flex-md-grow-1 flex-xl-grow-0">
              <button className="btn btn-sm btn-light bg-white dropdown-toggle" type="button" id="dropdownMenuDate2" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                <i className="mdi mdi-calendar"></i>Today &nbsp; {today.toLocaleDateString()}
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}