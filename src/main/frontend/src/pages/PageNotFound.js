import React from 'react';
import { Link } from 'react-router-dom';

const PageNotFound = () => {
  return (
    <section className='pageNotFound'>
      <code className='warning404'>404 Page Not Found</code>
      <p>The requested URL was not found on this server.</p>
      <Link to="/">
        Go to homepage
      </Link>
    </section>
  );
};

export default PageNotFound;
