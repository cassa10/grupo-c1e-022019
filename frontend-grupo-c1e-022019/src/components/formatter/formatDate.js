const formatDate = (t, date) => {
  const formatter = new Intl.DateTimeFormat(t('location'), {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
  });

  return (formatter.format(date));
};

export default formatDate;
