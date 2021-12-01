import React from 'react';
import Banner from '../../elements/ui/Banner';
import Header from '../../layout/Header';
import Footer from '../../layout/Footer';
import CreateJobsForm from '../../elements/widgets/Form/CreateJobsForm';

export default function CreateJobs() {
  return (
    <div id="wrap">
      <Header />
      <div className="container-scroller">
        <div className="container-fluid page-body-wrapper">
          {/* Banner는 왼쪽에 있는 리스트 형식의 메뉴 */}
          <Banner />
          {/* 여기부터 프사 누르면 나오는 메뉴 */}
          <div className="main-panel">
            <div className="content-wrapper">
                <CreateJobsForm/>
            </div>
            <Footer/>
          </div>
        </div>
      </div>
    </div>
  );
}