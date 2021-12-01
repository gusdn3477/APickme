import React from "react";
import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import "bootstrap-icons/font/bootstrap-icons.css";

export default function Banner() {
  return (
    <nav
      className="sidebar sidebar-offcanvas"
      id="sidebar"
      style={{ width: "300px" }}
    >
      <ul className="nav">
        <li className="nav-item">
          <Link className="nav-link" to="/">
            <i className="icon-grid menu-icon"></i>
            <span className="menu-title">Dashboard</span>
          </Link>
        </li>

        <li className="nav-item">
          <a
            className="nav-link"
            data-bs-toggle="collapse"
            href="#account-control"
            aria-expanded="false"
            aria-controls="account-control"
          >
            <i className="icon-layout menu-icon"></i>
            <span className="menu-title">계정관리(인사담당자)</span>
            <i className="menu-arrow"></i>
          </a>
          <div className="collapse" id="account-control">
            <ul className="nav flex-column sub-menu">
              <li className="nav-item">
                <Link className="nav-link" to="/hr/manage">
                  인사 담당자 관리
                </Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link" to="/hr/create">
                  인사 담당자 추가
                </Link>
              </li>
            </ul>
          </div>
        </li>

        <li className="nav-item">
          <a
            className="nav-link"
            data-bs-toggle="collapse"
            href="#jobs-control"
            aria-expanded="false"
            aria-controls="jobs-control"
          >
            <i className="icon-paper menu-icon"></i>
            <span className="menu-title">공고관리(인사담당자)</span>
            <i className="menu-arrow"></i>
          </a>
          <div className="collapse" id="jobs-control">
            <ul className="nav flex-column sub-menu">
              <li className="nav-item">
                <Link className="nav-link" to="/jobs">
                  공고 목록 조회
                </Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link" to="/jobs/create">
                  공고 생성
                </Link>
              </li>
            </ul>
          </div>
        </li>

        <li className="nav-item">
          <a
            className="nav-link"
            data-bs-toggle="collapse"
            href="#process-control"
            aria-expanded="false"
            aria-controls="process-control"
          >
            <i className="icon-layout menu-icon"></i>
            <span className="menu-title">필기전형관리(인사담당자)</span>
            <i className="menu-arrow"></i>
          </a>
          <div className="collapse" id="process-control">
            <ul className="nav flex-column sub-menu">
              <li className="nav-item">
                <Link className="nav-link" to="/process/written">
                  필기 전형 채점
                </Link>
              </li>
            </ul>
          </div>
        </li>

        <li className="nav-item">
          <a
            className="nav-link"
            data-bs-toggle="collapse"
            href="#interview-control"
            aria-expanded="false"
            aria-controls="interview-control"
          >
            <i className="icon-layout menu-icon"></i>
            <span className="menu-title">면접전형관리(인사담당자)</span>
            <i className="menu-arrow"></i>
          </a>
          <div className="collapse" id="interview-control">
            <ul className="nav flex-column sub-menu">
              <li className="nav-item">
                <Link className="nav-link" to="/process/interview">
                  면접 전형 관리
                </Link>
              </li>
            </ul>
          </div>
        </li>

        <li className="nav-item">
          <a
            className="nav-link"
            data-bs-toggle="collapse"
            href="#interviewee-control"
            aria-expanded="false"
            aria-controls="interviewee-control"
          >
            <i className="icon-layout menu-icon"></i>
            <span className="menu-title">지원자관리(인사담당자)</span>
            <i className="menu-arrow"></i>
          </a>
          <div className="collapse" id="interviewee-control">
            <ul className="nav flex-column sub-menu">
              <li className="nav-item">
                <Link className="nav-link" to="/hr/interviewlist">
                  면접자 리스트
                </Link>
              </li>
            </ul>
          </div>
        </li>

        <li className="nav-item">
          <a
            className="nav-link"
            data-bs-toggle="collapse"
            href="#jobs-find"
            aria-expanded="false"
            aria-controls="jobs-find"
          >
            <i className="icon-layout menu-icon"></i>
            <span className="menu-title">공고보기(지원자 관점)</span>
            <i className="menu-arrow"></i>
          </a>
          <div className="collapse" id="jobs-find">
            <ul className="nav flex-column sub-menu">
              <li className="nav-item">
                <Link className="nav-link" to="/jobs">
                  공고목록
                </Link>
              </li>
            </ul>
          </div>
        </li>

        <li className="nav-item">
          <a
            className="nav-link"
            data-bs-toggle="collapse"
            href="#mypage"
            aria-expanded="false"
            aria-controls="mypage"
          >
            <i className="icon-layout menu-icon"></i>
            <span className="menu-title">마이페이지</span>
            <i className="menu-arrow"></i>
          </a>
          <div className="collapse" id="mypage">
            <ul className="nav flex-column sub-menu">
              <li className="nav-item">
                <Link className="nav-link" to="/hr/check-password">
                  내 정보 수정(HR)
                </Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link" to="/user/check-password">
                  내 정보 수정(사용자)
                </Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link" to="/user/mylist">
                  나의 지원 현황(사용자)
                </Link>
              </li>
            </ul>
          </div>
        </li>
      </ul>
      {/* 부트스트랩 5는 되는데 4는 토글이 안됨. */}s
    </nav>
  );
}
