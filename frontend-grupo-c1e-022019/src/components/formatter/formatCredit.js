const formatCredit = (t, amount) => {
  const formatter = new Intl.NumberFormat(t('location'), {
    style: 'currency',
    currency: 'USD',
    minimumFractionDigits: 2,
  });

  return (formatter.format(amount));
};

export default formatCredit;
