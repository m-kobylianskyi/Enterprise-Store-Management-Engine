INSERT INTO sim_clock (id, sim_time, speed_multiplier, is_running)
VALUES (1, '2026-01-01 08:00:00', 1.00, false);

INSERT INTO sim_config (config_key, config_value) VALUES
                                                      ('seed', '12345'),
                                                      ('customers_per_hour', '20'),
                                                      ('points_per_currency_unit', '1'),
                                                      ('points_expire_days', '365'),
                                                      ('payroll_day_of_month', '1');