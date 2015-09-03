--
-- PostgreSQL database dump
--

-- Dumped from database version 9.1.13
-- Dumped by pg_dump version 9.3.1
-- Started on 2015-09-03 20:43:43

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 175 (class 3079 OID 11645)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 1936 (class 0 OID 0)
-- Dependencies: 175
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 164 (class 1259 OID 16407)
-- Name: game; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE game (
    id integer NOT NULL,
    red1 integer,
    red2 integer,
    blue1 integer,
    blue2 integer,
    "redScore" integer,
    "blueScore" integer,
    date date,
    CONSTRAINT game_check CHECK (((((((red1 <> red2) AND (red1 <> blue1)) AND (red1 <> blue2)) AND (red2 <> blue1)) AND (red2 <> blue2)) AND (blue1 <> blue2))),
    CONSTRAINT score_check CHECK (((((("redScore" = 10) OR ("blueScore" = 10)) AND (("redScore" >= 0) AND ("redScore" <= 10))) AND (("blueScore" >= 0) AND ("blueScore" <= 10))) AND (("redScore" < 10) OR ("blueScore" < 10))))
);


ALTER TABLE public.game OWNER TO postgres;

--
-- TOC entry 163 (class 1259 OID 16405)
-- Name: game_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE game_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.game_id_seq OWNER TO postgres;

--
-- TOC entry 1937 (class 0 OID 0)
-- Dependencies: 163
-- Name: game_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE game_id_seq OWNED BY game.id;


--
-- TOC entry 161 (class 1259 OID 16385)
-- Name: player; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE player (
    id integer NOT NULL,
    name text,
    active boolean
);


ALTER TABLE public.player OWNER TO postgres;

--
-- TOC entry 171 (class 1259 OID 16471)
-- Name: games_active_view; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW games_active_view AS
SELECT game."redScore", game."blueScore", game.date, r1.name AS "playerR1", r2.name AS "playerR2", b1.name AS "playerB1", b2.name AS "playerB2" FROM game, player r1, player r2, player b1, player b2 WHERE (((((((((game.red1 = r1.id) AND (game.red2 = r2.id)) AND (game.blue1 = b1.id)) AND (game.blue2 = b2.id)) AND (r1.active = true)) AND (r2.active = true)) AND (b1.active = true)) AND (b2.active = true)) AND (game.date > (('now'::text)::date - '1 mon'::interval))) ORDER BY game.id;


ALTER TABLE public.games_active_view OWNER TO postgres;

--
-- TOC entry 173 (class 1259 OID 24576)
-- Name: games_current_month; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW games_current_month AS
SELECT game."redScore", game."blueScore", game.date, r1.name AS "playerR1", r2.name AS "playerR2", b1.name AS "playerB1", b2.name AS "playerB2" FROM game, player r1, player r2, player b1, player b2 WHERE (((((((((game.red1 = r1.id) AND (game.red2 = r2.id)) AND (game.blue1 = b1.id)) AND (game.blue2 = b2.id)) AND (r1.active = true)) AND (r2.active = true)) AND (b1.active = true)) AND (b2.active = true)) AND (date_trunc('month'::text, (game.date)::timestamp with time zone) = date_trunc('month'::text, (('now'::text)::date)::timestamp with time zone))) ORDER BY game.id;


ALTER TABLE public.games_current_month OWNER TO postgres;

--
-- TOC entry 174 (class 1259 OID 24581)
-- Name: games_previous_month; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW games_previous_month AS
SELECT game."redScore", game."blueScore", game.date, r1.name AS "playerR1", r2.name AS "playerR2", b1.name AS "playerB1", b2.name AS "playerB2" FROM game, player r1, player r2, player b1, player b2 WHERE (((((((((game.red1 = r1.id) AND (game.red2 = r2.id)) AND (game.blue1 = b1.id)) AND (game.blue2 = b2.id)) AND (r1.active = true)) AND (r2.active = true)) AND (b1.active = true)) AND (b2.active = true)) AND (date_trunc('month'::text, (game.date)::timestamp with time zone) = date_trunc('month'::text, ((((('now'::text)::date - '1 mon'::interval))::text)::date)::timestamp with time zone))) ORDER BY game.id;


ALTER TABLE public.games_previous_month OWNER TO postgres;

--
-- TOC entry 169 (class 1259 OID 16461)
-- Name: games_view; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW games_view AS
SELECT game."redScore", game."blueScore", game.date, r1.name AS "playerR1", r2.name AS "playerR2", b1.name AS "playerB1", b2.name AS "playerB2" FROM game, player r1, player r2, player b1, player b2 WHERE ((((game.red1 = r1.id) AND (game.red2 = r2.id)) AND (game.blue1 = b1.id)) AND (game.blue2 = b2.id)) ORDER BY game.id;


ALTER TABLE public.games_view OWNER TO postgres;

--
-- TOC entry 165 (class 1259 OID 16440)
-- Name: play_view; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW play_view AS
SELECT player.id, player.name, player.active, game."redScore" AS score FROM game, player WHERE (((player.id = game.red1) OR (player.id = game.red2)) AND (player.active = true)) UNION ALL SELECT player.id, player.name, player.active, game."blueScore" AS score FROM game, player WHERE (((player.id = game.blue1) OR (player.id = game.blue2)) AND (player.active = true));


ALTER TABLE public.play_view OWNER TO postgres;

--
-- TOC entry 167 (class 1259 OID 16448)
-- Name: lose_view; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW lose_view AS
SELECT play_view.id, count(play_view.id) AS count FROM play_view WHERE (play_view.score <> 10) GROUP BY play_view.id;


ALTER TABLE public.lose_view OWNER TO postgres;

--
-- TOC entry 172 (class 1259 OID 16475)
-- Name: player_view; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW player_view AS
SELECT player.id, player.name, player.active FROM player WHERE (player.active = true) ORDER BY player.name;


ALTER TABLE public.player_view OWNER TO postgres;

--
-- TOC entry 162 (class 1259 OID 16388)
-- Name: players_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE players_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.players_id_seq OWNER TO postgres;

--
-- TOC entry 1938 (class 0 OID 0)
-- Dependencies: 162
-- Name: players_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE players_id_seq OWNED BY player.id;


--
-- TOC entry 170 (class 1259 OID 16466)
-- Name: tmp_team_score_ratio_view; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW tmp_team_score_ratio_view AS
SELECT tbl.player1, tbl.player2, avg(tbl.score_ratio) AS avg FROM (SELECT p1.id AS player1, p2.id AS player2, (game."redScore" - game."blueScore") AS score_ratio FROM player p1, player p2, game WHERE (((((p1.id <> p2.id) AND (p1.active = true)) AND (p2.active = true)) AND (p1.id > p2.id)) AND (((game.red1 = p1.id) AND (game.red2 = p2.id)) OR ((game.red1 = p2.id) AND (game.red2 = p1.id)))) UNION ALL SELECT p1.id, p2.id, (game."blueScore" - game."redScore") AS score_ratio FROM player p1, player p2, game WHERE (((((p1.id <> p2.id) AND (p1.active = true)) AND (p2.active = true)) AND (p1.id > p2.id)) AND (((game.blue1 = p1.id) AND (game.blue2 = p2.id)) OR ((game.blue1 = p2.id) AND (game.blue2 = p1.id))))) tbl GROUP BY tbl.player1, tbl.player2;


ALTER TABLE public.tmp_team_score_ratio_view OWNER TO postgres;

--
-- TOC entry 166 (class 1259 OID 16444)
-- Name: win_view; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW win_view AS
SELECT play_view.id, count(play_view.id) AS count FROM play_view WHERE (play_view.score = 10) GROUP BY play_view.id;


ALTER TABLE public.win_view OWNER TO postgres;

--
-- TOC entry 168 (class 1259 OID 16456)
-- Name: win_stats_view; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW win_stats_view AS
SELECT player.name, COALESCE(win_view.count, (0)::bigint) AS wins, COALESCE(lose_view.count, (0)::bigint) AS losses, (COALESCE(win_view.count, (0)::bigint) + COALESCE(lose_view.count, (0)::bigint)) AS sum, round((COALESCE((win_view.count)::numeric, (0)::numeric) / ((COALESCE(win_view.count, (0)::bigint) + COALESCE(lose_view.count, (0)::bigint)))::numeric), 2) AS ratio FROM ((player LEFT JOIN win_view ON ((player.id = win_view.id))) LEFT JOIN lose_view ON ((player.id = lose_view.id))) WHERE ((player.active = true) AND ((win_view.count IS NOT NULL) OR (lose_view.count IS NOT NULL))) ORDER BY round((COALESCE((win_view.count)::numeric, (0)::numeric) / ((COALESCE(win_view.count, (0)::bigint) + COALESCE(lose_view.count, (0)::bigint)))::numeric), 2) DESC;


ALTER TABLE public.win_stats_view OWNER TO postgres;

--
-- TOC entry 1799 (class 2604 OID 16410)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY game ALTER COLUMN id SET DEFAULT nextval('game_id_seq'::regclass);


--
-- TOC entry 1798 (class 2604 OID 16390)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY player ALTER COLUMN id SET DEFAULT nextval('players_id_seq'::regclass);


--
-- TOC entry 1809 (class 2606 OID 16412)
-- Name: game_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY game
    ADD CONSTRAINT game_pkey PRIMARY KEY (id);


--
-- TOC entry 1803 (class 2606 OID 16398)
-- Name: players_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY player
    ADD CONSTRAINT players_pkey PRIMARY KEY (id);


--
-- TOC entry 1804 (class 1259 OID 16430)
-- Name: fki_IDX_B1; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX "fki_IDX_B1" ON game USING btree (blue1);


--
-- TOC entry 1805 (class 1259 OID 16436)
-- Name: fki_IDX_B2; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX "fki_IDX_B2" ON game USING btree (blue2);


--
-- TOC entry 1806 (class 1259 OID 16418)
-- Name: fki_IDX_R1; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX "fki_IDX_R1" ON game USING btree (red1);


--
-- TOC entry 1807 (class 1259 OID 16424)
-- Name: fki_IDX_R2; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX "fki_IDX_R2" ON game USING btree (red2);


--
-- TOC entry 1812 (class 2606 OID 16425)
-- Name: IDX_B1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY game
    ADD CONSTRAINT "IDX_B1" FOREIGN KEY (blue1) REFERENCES player(id);


--
-- TOC entry 1813 (class 2606 OID 16431)
-- Name: IDX_B2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY game
    ADD CONSTRAINT "IDX_B2" FOREIGN KEY (blue2) REFERENCES player(id);


--
-- TOC entry 1810 (class 2606 OID 16413)
-- Name: IDX_R1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY game
    ADD CONSTRAINT "IDX_R1" FOREIGN KEY (red1) REFERENCES player(id);


--
-- TOC entry 1811 (class 2606 OID 16419)
-- Name: IDX_R2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY game
    ADD CONSTRAINT "IDX_R2" FOREIGN KEY (red2) REFERENCES player(id);


--
-- TOC entry 1935 (class 0 OID 0)
-- Dependencies: 5
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2015-09-03 20:43:45

--
-- PostgreSQL database dump complete
--

