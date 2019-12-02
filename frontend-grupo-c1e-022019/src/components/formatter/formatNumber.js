const formatNumber = (t, number) => {
  const formatter = new Intl.NumberFormat(t('location'), {
    style: 'decimal',
  });

  return (formatter.format(number));
};

export default formatNumber;
