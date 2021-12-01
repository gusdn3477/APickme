import React, { useState } from 'react';
import Banner from '../../elements/ui/Banner';
import Header from '../../layout/Header';
import Footer from '../../layout/Footer';
import Card from '../../elements/widgets/Home/Card';
import { useEffect } from 'react';
import Pagination from '../../../utilities/Pagination';
import { paginate } from '../../../utilities/paginate';

// Jobs list로 보시면 됩니다.
export default function Buttons() {

  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [jobpage, setJobpage] = useState({
    jobdata: [],
    pageSize: 4,
    currentPage: 1,
  });

  useEffect(() => {
    fetch(`/job-service/jobs`)
      .then(res => {
        return res.json();
      })
      .then(data => {
        setData(data);
        console.log(data);
        setLoading(false);
      });
  }, []);
  let data2 = data.filter(value => value.closed !== "T");
  console.log(data2);

  const handlePageChange = (page) => {
    setLoading(true)
    setJobpage({ ...jobpage, currentPage: page });
    setLoading(false)
  };

  const { jobdata, pageSize, currentPage } = jobpage;
  const pagedJobs = paginate(data2, currentPage, pageSize);

  console.log(pagedJobs);

  if (loading) return <div class="spinner-border text-primary" role="status"></div>;
  return (
    <div id="wrap">
      <Header />
      <div className="container-scroller">
        <div className="container-fluid page-body-wrapper">
          <Banner />
          <div className="main-panel">
            <div className="content-wrapper">
              <div className="row">
                {
                  pagedJobs.length > 0 && pagedJobs.map(
                    (item, idx) => (
                      <Card
                        idx={idx + 1}
                        key={item.idx}
                        data={item}
                      />
                    )
                  )
                }
              </div>
              <div >
                <Pagination
                  itemsCount={data2.length}
                  pageSize={pageSize}
                  currentPage={currentPage}
                  onPageChange={handlePageChange}
                /></div>
            </div>
            <Footer />
          </div>
        </div>
      </div>
    </div>
  );
}