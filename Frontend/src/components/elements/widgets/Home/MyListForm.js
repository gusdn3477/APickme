import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import MyProcessCard from "./MyProcessCard";

export default function MyListForm({ idx, key, data }) {
  const [written, setWritten] = useState([]);
  const [interview, setInterview] = useState([]);
  const [loading, setLoading] = useState(true);
  const [loading2, setLoading2] = useState(true);
  const [loading3, setLoading3] = useState(true);
  const [applyInfo, setApplyInfo] = useState([]);

  const [jobdata, setJobdata] = useState("");
  const [intv1Start, setItv1start] = useState("");
  const [intv2Start, setItv2start] = useState("");
  const [intv1End, setItv1End] = useState("");
  const [intv2End, setItv2End] = useState("");

  useEffect(() => {
    fetch(
      `/process-service/process/written/${data.jobsNo}/${localStorage.getItem(
        "userId"
      )}`
    )
      .then((res) => {
        return res.json();
      })
      .then((data) => {
        console.log("필기 결과", data);
        setWritten(data);
        
        return data;
      })
      .then((res) => {});

    fetch(
      `/process-service/process/interview/${data.jobsNo}/${localStorage.getItem(
        "userId"
      )}`
    )
      .then((res) => res.json())
      .then((data) => {
        console.log("면접 결과", data);
        setInterview(data);
      });

    // 지원내역 가져오는 부분
    fetch(
      `/user-service/users/apply/${localStorage.getItem("userId")}/${
        data.jobsNo
      }`
    )
      .then((res) => {
        return res.json();
      })
      .then((res) => {
        setApplyInfo(res);
        console.log("data", applyInfo);
        setLoading(false);
      });

    fetch(
        `/job-service/jobs/${data.jobsNo}`
    )
    .then((res)=>{
        return res.json();
    })
    .then((data2)=>{
        console.log("공고내용", data2);
        setJobdata(data2.jobsTitle);
        setItv1start(data2.intv1Start);
        setItv1End(data2.intv1End);
        setItv2start(data2.intv2Start);
        setItv2End(data2.intv2End);
        console.log("jobtitle", jobdata)
      //   setCorpname(d)
      
        return data2.jobsTitle;
    });
    


  }, []);
  console.log("데이터를확인", data)
  console.log("jobtitle", jobdata)

  // 이걸로 결과 얻기
  if (loading)
    return (
      <div className="spinner-border text-primary" role="status">

      </div>
    );

  return (
    <tr>
      <td>{idx}</td>
      <td>
        <Link to={`/jobs/${data.jobsNo}`}>{data.jobsTitle}</Link>
      </td>
      <td className="font-weight-bold">{data.corpName}</td>
      <td>{data.employType}</td>

      {/* <td>{data.createdAt}</td> */}

      <td>{data.jobLocation}</td>
      <td>
        {/* <MyApplyListForm data2={data} /> */}
        <div>
          <button
            type="button"
            className="btn btn-primary"
            data-bs-toggle="modal"
            data-bs-target={`#exampleModal` + idx}
          >
            내 지원서 보기
          </button>
        </div>
        <div
          className="modal fade"
          id={`exampleModal` + idx}
          tabindex="-1"
          aria-labelledby="exampleModalLabel"
          aria-hidden="true"
        >
          <div className="modal-dialog">
            <div className="modal-content">
              <div className="modal-header">
                <h5 className="modal-title" id="exampleModalLabel">
                  내 지원서
                </h5>
                <button
                  type="button"
                  className="btn-close"
                  data-bs-dismiss="modal"
                  aria-label="Close"
                ></button>
              </div>
              <div className="modal-body" style={{paddingLeft:"3rem"}} value={data}>
                <h3 className="modal-text">최종 지원이 완료되었습니다.</h3>
                <div className="modal-text-sub" style={{padding:"1.3rem 1.3rem .5rem 1.3rem"}}>
                <br></br> 이름 : {applyInfo.applyName} <br></br>
                <br></br> 이메일 : {applyInfo.applyEmail} <br></br>
                <br></br> 전화번호 : {applyInfo.applyContact} <br></br>
                <br></br> 지원날짜 : {applyInfo.applyDateTime ? applyInfo.applyDateTime.substring(0, 10) : ""}<br></br>
                {/* <br></br> 지원날짜 : {applyInfo.applyDateTime} */}
                {/* <br></br> data.jobsNo : {data.jobsNo} <br></br> */}
                <br></br> 지원번호 : {applyInfo.jobsNo ? applyInfo.jobsNo.substring(0, 8) : ""} <br></br>
              </div></div>
              {/* <div>{applyList}</div> */}
              <div className="modal-footer">
                <button
                  type="button"
                  className="btn btn-secondary"
                  data-bs-dismiss="modal"
                >
                  닫기
                </button>
              </div>
            </div>
          </div>
        </div>
      </td>
      <td>
        <button
          type="button"
          className="btn btn-primary"
          data-bs-toggle="modal"
          data-bs-target={`#exampleModalTwo` + idx}
        >
          확인하기
        </button>
      </td>

      <div
        className="modal fade"
        id={`exampleModalTwo` + idx}
        tabindex="-1"
        aria-labelledby="exampleModalLabel"
        aria-hidden="true"
      >
        <div className="modal-dialog">
          <div className="modal-content">
            <div className="modal-header">
              <h5 className="modal-title" id="exampleModalLabel">
                지원 결과
              </h5>
              <button
                type="button"
                className="btn-close"
                data-bs-dismiss="modal"
                aria-label="Close"
              ></button>
            </div>
            <div className="modal-body" >
              <MyProcessCard
              data = {written}
              interview = {interview}
              corpName = {data.corpName}
              jobtitle = {data.jobsTitle}
              intv1Start = {intv1Start}
              intv2Start = {intv2Start}
              intv1End = {intv1End}
              intv2End = {intv2End}
              />
            </div>
            <div className="modal-footer">
              <button
                type="button"
                className="btn btn-secondary"
                data-bs-dismiss="modal"
              >
                닫기
              </button>
            </div>
          </div>
        </div>
      </div>
    </tr>
  );
}
