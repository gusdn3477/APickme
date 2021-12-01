import { data } from "jquery";
import { useState, useEffect } from "react";

export default function MyApplyListForm({ data2 }) {
  const [applyInfo, setApplyInfo] = useState([data2]);

  console.log(data2); //jobsNo

  console.log(applyInfo.jobsNo);
  const [applyJobsNo, setApplyJobsNo] = useState([data2]);

  console.log("applyInfo :" + data2.jobsNo);

  useEffect(() => {
    fetch(
      `/user-service/users/apply/${localStorage.getItem("userId")}/${
        data2.jobsNo
      }`
    )
      .then((res) => {
        return res.json();
      })
      .then((event) => {
        console.log(event);
        setApplyInfo(event);
        console.log(applyInfo);
      });
  }, []);

  console.log(applyInfo);

  function gogo(data2) {}

  return (
    // <div>
    //   <button onClick={gogo(data2)}>내 지원서 보기기기기</button>
    // </div>
    <div>
      <div>
        <button
          type="button"
          class="btn btn-primary"
          data-bs-toggle="modal"
          data-bs-target="#exampleModal"
        >
          내 지원서 보기 {data2.jobsNo}
        </button>
      </div>
      <div
        class="modal fade"
        id="exampleModal"
        tabindex="-1"
        aria-labelledby="exampleModalLabel"
        aria-hidden="true"
      >
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title" id="exampleModalLabel">
                내 지원서
              </h5>
              <button
                type="button"
                class="btn-close"
                data-bs-dismiss="modal"
                aria-label="Close"
              ></button>
            </div>
            <div class="modal-body">
              <h3>최종 지원이 완료되었습니다.</h3>
              <br></br> 이름 : {applyInfo.applyName} <br></br>
              <br></br> 이메일 : {applyInfo.applyEmail} <br></br>
              <br></br> 전화번호 : {applyInfo.applyContact} <br></br>
              <br></br> 지원날짜 : {applyInfo.applyDateTime} <br></br>
              <br></br> data.jobsNo : {data2.jobsNo} <br></br>
              <br></br> applyInfo.jobsNo : {applyInfo.jobsNo} <br></br>
              <br></br> value.jobsNo : $value <br></br>
            </div>
            <div class="modal-footer">
              <button
                type="button"
                class="btn btn-secondary"
                data-bs-dismiss="modal"
              >
                닫기
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
