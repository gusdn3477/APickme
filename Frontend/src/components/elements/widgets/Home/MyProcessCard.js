import { useState, useEffect } from "react";
import { Link } from "react-router-dom";

export default function MyProcessCard({ data, interview, corpName, jobtitle
    , intv1Start, intv2Start, intv1End, intv2End }) {
    console.log('data 확인용', data)
    console.log('corpName', corpName)
    const [jobdata, setJobdata] = useState("");
    const [loading, setLoading] = useState(true);
    const [data2, setData2] = useState();

    useEffect(() => {
        fetch(`/job-service/jobs/${data.jobsNo}`)
            .then((res) => {
                return res.json();
            })
            .then((data2) => {
                console.log("공고내용", data2);
                setJobdata(data2.jobsTitle);
                return data2.jobsTitle;
            });

        if (localStorage.getItem('userId')) {
            fetch(`/user-service/users/${localStorage.getItem('userId')}`)
                .then(res => {
                    return res.json();
                })
                .then(data => {
                    setData2(data);
                    console.log(data);
                    setLoading(false);
                })
        }

    }, []);
    console.log("공고명", jobdata);
    console.log(interview)

    if (loading)
        return (
            <div class="spinner-border text-primary" role="status"></div>
        );

    if (!data.writtenResult) {
        return (<><h4 style={{lineHeight:"130%"}}>안녕하세요 <b>{data2.name}</b>님 <br/> <b className="google-font-force_l">{corpName}</b>입니다.</h4>
            <hr></hr>
            <div>먼저 <b className="google-font-force_mid">{jobtitle} 공고</b>에 지원해 주신 것에 감사의 말씀 드립니다.<br></br>
            <b>{data2.name}</b>님은 현재 필기전형 진행중이며, 결과가 나오는 대로 알려드리겠습니다.<br/><br/>
            다시 한 번 지원해 주셔서 감사합니다.<br/><br/> {corpName} 채용담당자 드림</div></>)
    }
    else {
        if (data.writtenResult === "P") {
            if (!interview.firstInterviewResult) {
                return (
                    <><h4 style={{lineHeight:"130%"}}> {jobtitle} <br/>서류전형 결과 안내드립니다.</h4>
                    <hr/>
                    <div>안녕하십니까 <b>{data2.name}</b>님, <b className="google-font-force_l">{corpName}</b>입니다.<br></br>
                        먼저 <b className="google-font-force_mid">{jobtitle} 공고</b>에 지원해주셔서 감사합니다.<br></br><br/>
                        <b>{data2.name}</b>님의 서류전형 합격을 진심으로 축하드립니다.<br></br>
                        향후 전형에서도 좋은 결과 있으시길 진심으로 기원합니다.<br/><br/>
                        전형 관련 일정을 아래와 같이 안내드리오니, 일정에 맞춰 준비 및 참석 바랍니다.<br/><br/>
                        1차 실무 면접 예정일은
                        {intv1Start ? intv1Start.substring(0, 10) : ""} -
                        {intv1End ? intv1End.substring(0, 10) : ""} 입니다 <br></br>
                        구체적인 면접전형 일자는 추후에 공지하겠습니다<br/><br/><br/>
                        감사합니다<br/><br/> {corpName} 채용담당자 드림</div></>
                );
            }
            else {
                if (interview.firstInterviewResult === "F") {
                    return (<><h4 style={{lineHeight:"130%"}}>  {jobtitle} <br/>1차 면접 전형 결과 안내드립니다.</h4>
                        <hr/>
                        <div>안녕하십니까 <b>{data2.name}</b>님, <b className="google-font-force_l">{corpName}</b>입니다.<br></br>
                            먼저 <b className="google-font-force_mid">{jobtitle} 공고</b> 채용에 관심을 가지고 귀한 시간을 내주셔서 감사드립니다.
        <br></br><br/>
                            <b>{data2.name}</b>님께서는 아쉽게도 이번 실무 면접 전형에 불합격 하셨습니다.<br></br>
                            다음번에 더 좋은 인연으로 만나뵙길 희망하며,<br/><br/>
                            앞으로 하시는 일 모두 건승하시길 바랍니다.<br/><br/>
                            <b className="google-font-force_mid">{corpName}</b>에 지원해주셔서 다시 한 번 진심으로 감사드립니다.<br/><br/><br/>
                            감사합니다<br/><br/> <b  className="google-font-force_mid">{corpName}</b> 채용담당자 드림</div></>
                        )
                }
                else {
                    if (!interview.secondInterviewResult) {
                        return (<><h4 style={{lineHeight:"130%"}}>  {jobtitle} <br/>1차 실무 면접 결과 안내드립니다.</h4>
                            <hr/>
                            <div>안녕하십니까 <b>{data2.name}</b>님, <b className="google-font-force_L">{corpName}</b>입니다.<br></br>
                                먼저 <b className="google-font-force_mid">{jobtitle} 공고</b>에 지원해주셔서 감사합니다.<br></br><br/>
                                <b>{data2.name}</b>님의 면접 전형 합격을 진심으로 축하드립니다.<br></br>
                                향후 전형에서도 좋은 결과 있으시길 진심으로 기원합니다.<br/><br/>
                                전형 관련 일정을 아래와 같이 안내드리오니, 일정에 맞춰 준비 및 참석 바랍니다.<br/><br/>
                                2차 최종 면접 예정일은
                                {intv2Start ? intv2Start.substring(0, 10) : ""} -
                                {intv2End ? intv2End.substring(0, 10) : ""} 입니다 <br></br>
                                구체적인 전형 일자는 추후에 공지하겠습니다<br/><br/><br/>
                                감사합니다<br/><br/> {corpName} 채용담당자 드림</div></>);
                    }
                    else {
                        if (interview.secondInterviewResult === "P") {
                            return (<><h4 style={{lineHeight:"130%"}}>  {jobtitle} <br/>최종 면접 전형 결과 안내드립니다.</h4>
                                <hr/>
                                <div>안녕하십니까 <b>{data2.name}</b>님, <b className="google-font-force_l">{corpName}</b>입니다.<br></br>
                                    먼저 <b className="google-font-force_mid">{jobtitle} 공고</b>에 지원해주셔서 감사합니다.<br></br><br/>
                                    <b>{data2.name}</b>님의 최종 합격을 진심으로 축하드립니다.<br></br>
                                    신체검사 및 구체적인 채용 진행 안내사항을 지원서에 기재된 메일로 송달할 예정이니, 반드시 관련 메일 확인해 주시기 바랍니다.<br/><br/><br/>
                                    감사합니다<br/><br/> <b className="google-font-force_l">{corpName}</b> 채용담당자 드림</div></>
                                );
                        }
                        else {
                            return (<><h4 style={{lineHeight:"130%"}}>  {jobtitle} <br/>최종 면접 결과 안내드립니다.</h4>
                                <hr/>
                                <div>안녕하십니까 <b>{data2.name}</b>님, <b className="google-font-force_l">{corpName}</b>입니다.<br></br>
                                    먼저 <b className="google-font-force_mid">{jobtitle} 공고</b> 채용에 관심을 가지고 귀한 시간을 내주셔서 감사드립니다.
                <br></br><br/>
                                    <b>{data2.name}</b>님께서는 아쉽게도 이번 2차 면접 전형에 불합격 하셨습니다.<br></br>
                                    다음번에 더 좋은 인연으로 만나뵙길 희망하며,<br/><br/>
                                    앞으로 하시는 일 모두 건승하시길 바랍니다.<br/><br/>
                                    {corpName}에 지원해주셔서 다시 한 번 진심으로 감사드립니다.<br/><br/><br/>
                                    감사합니다<br/><br/> <b className="google-font-force_l">{corpName}</b> 채용담당자 드림</div></>);
                        }
                    }

                }
            }
        }
        else {
            return (
                <><h4 style={{lineHeight:"130%"}}>  {jobtitle} <br/>서류전형 결과 안내드립니다.</h4>
                <hr/>
                <div>안녕하십니까 <b>{data2.name}</b>님, <b className="google-font-force_l">{corpName}</b>입니다.<br></br>
                    먼저 <b className="google-font-force_mid">{jobtitle} 공고</b> 채용에 관심을 가지고 귀한 시간을 내주셔서 감사드립니다.
<br></br><br/>
                    <b>{data2.name}</b>님께서는 아쉽게도 이번 서류전형에 불합격 하셨습니다.<br></br>
                    다음번에 더 좋은 인연으로 만나뵙길 희망하며,<br/><br/>
                    앞으로 하시는 일 모두 건승하시길 바랍니다.<br/><br/>
                    <b className="google-font-force_l">{corpName}</b>에 지원해주셔서 다시 한 번 진심으로 감사드립니다.<br/><br/><br/>
                    감사합니다<br/><br/> <b className="google-font-force_l">{corpName}</b> 채용담당자 드림</div></>
            );
        }
    }



}
