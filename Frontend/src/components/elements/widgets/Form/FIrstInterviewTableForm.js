import { useState, useEffect } from "react";
import { ChangeEmpNoString } from "../../../../utilities/ChangeString";

export default function FirstInterviewTableForm({ idx, key, data, jobsNo, setData, passOrNonPass }) {

  const [values, setValues] = useState('');
  const [interviwer, SetInterviewer] = useState(data.firstInterviewer);
  const [loading, setLoading] = useState(false);


  const name2 = ChangeEmpNoString(data.firstInterviewer)
  const name3 = ChangeEmpNoString(data.firstCheck)
  console.log("name", name3);

  const score = () => {
  
    if(data.firstCheck){
      alert("이미 합/불 여부가 결정된 전형은 재채점이 불가능합니다.");
      return;
    }
    if(values.score === undefined){
      alert("빈 칸은 입력할 수 없습니다.");
      return;
    }
    if(values.score < 0 || values.score > 100){
      alert("0과 100 사이의 숫자만 입력해 주세요.");
      return;
    }
    fetch(`/process-service/process/first-interview/score`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        applyNum: data.applyNum,
        firstInterviewScore: values.score,
        firstInterviewer: localStorage.getItem('empNo')
      }),
    })
      .then(res => {
        return res;
      })
      .then(
        res =>
          fetch(`/process-service/process/first-interview/${jobsNo}`)
            .then(res => {
              return res.json();
            })
        .then(res => {
          setData(res);
          console.log('res', res);
          setLoading(false);
          alert("채점 완료!");
        })
        )
  }

  const handleChangeForm = (e) => {
    setValues({
      ...values,
      [e.target.name]: e.target.value
    });
  }

  const useConfirm = (message = null, onConfirm, onCancel, deleteHR) => {
    if (!onConfirm || typeof onConfirm !== "function") {
      return;
    }
    if (onCancel && typeof onCancel !== "function") {
      return;
    }

    const confirmAction = () => {
      if (window.confirm(message)) {
        onConfirm();
        deleteHR();
      } else {
        onCancel();
      }
    };
    return confirmAction;
  };

  const deleteConfirm = () => 1;
  const cancelConfirm = () => 0;

  const confirmScore = useConfirm(
    "채점하시겠습니까?",
    deleteConfirm,
    cancelConfirm,
    score
  );

  return (
    <tr>
      <td style={{paddingLeft:"2rem"}}>{idx}</td>
      <td>{data.applyNum ? (data.applyNum).substring(0,8) : ""}</td>
      <td><input style={{width:"70%"}} type="number" class="form-control" id="exampleInputPassword1" name="score" placeholder="Only Number" onChange={handleChangeForm} /></td>
      <td><button type="button" className="btn btn-primary" onClick={confirmScore}>채점하기</button></td>
      <td>{data.firstInterviewScore}</td>
      <td>{data.firstInterviewResult ? data.firstInterviewResult : "미정"}</td>
      {/* <td>{passOrNonPass ? (passOrNonPass.firstInterviewResult) : ""}</td> */}
      <td style={{paddingLeft:"2rem"}}>{data.firstInterviewer ? name2 : ""}</td>
      <td style={{paddingLeft:"2rem"}}>{data.firstCheck ? name3 : ""}</td>
    </tr>
  );
}

// 101번줄 firstinterviewer test (data.firstInterviewer).substring(0,8)
// 102번줄 (data.firstCheck).substring(0,8)