--
-- PostgreSQL database dump
--

-- Dumped from database version 14.4 (Debian 14.4-1.pgdg110+1)
-- Dumped by pg_dump version 14.1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: r_cluster; Type: TABLE; Schema: public; Owner: rossonet
--

CREATE TABLE public.r_cluster (
    id_cluster bigint NOT NULL,
    name character varying(255),
    base_port character varying(255),
    sockets_buffer_size character varying(255),
    sockets_flush_interval character varying(255),
    sockets_compressed boolean,
    dynamic_cluster boolean
);


ALTER TABLE public.r_cluster OWNER TO rossonet;

--
-- Name: r_cluster_id_cluster_seq; Type: SEQUENCE; Schema: public; Owner: rossonet
--

CREATE SEQUENCE public.r_cluster_id_cluster_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.r_cluster_id_cluster_seq OWNER TO rossonet;

--
-- Name: r_cluster_id_cluster_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: rossonet
--

ALTER SEQUENCE public.r_cluster_id_cluster_seq OWNED BY public.r_cluster.id_cluster;


--
-- Name: r_cluster_slave; Type: TABLE; Schema: public; Owner: rossonet
--

CREATE TABLE public.r_cluster_slave (
    id_cluster_slave bigint NOT NULL,
    id_cluster integer,
    id_slave integer
);


ALTER TABLE public.r_cluster_slave OWNER TO rossonet;

--
-- Name: r_cluster_slave_id_cluster_slave_seq; Type: SEQUENCE; Schema: public; Owner: rossonet
--

CREATE SEQUENCE public.r_cluster_slave_id_cluster_slave_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.r_cluster_slave_id_cluster_slave_seq OWNER TO rossonet;

--
-- Name: r_cluster_slave_id_cluster_slave_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: rossonet
--

ALTER SEQUENCE public.r_cluster_slave_id_cluster_slave_seq OWNED BY public.r_cluster_slave.id_cluster_slave;


--
-- Name: r_condition; Type: TABLE; Schema: public; Owner: rossonet
--

CREATE TABLE public.r_condition (
    id_condition bigint NOT NULL,
    id_condition_parent integer,
    negated boolean,
    operator character varying(255),
    left_name character varying(255),
    condition_function character varying(255),
    right_name character varying(255),
    id_value_right integer
);


ALTER TABLE public.r_condition OWNER TO rossonet;

--
-- Name: r_condition_id_condition_seq; Type: SEQUENCE; Schema: public; Owner: rossonet
--

CREATE SEQUENCE public.r_condition_id_condition_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.r_condition_id_condition_seq OWNER TO rossonet;

--
-- Name: r_condition_id_condition_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: rossonet
--

ALTER SEQUENCE public.r_condition_id_condition_seq OWNED BY public.r_condition.id_condition;


--
-- Name: r_database; Type: TABLE; Schema: public; Owner: rossonet
--

CREATE TABLE public.r_database (
    id_database bigint NOT NULL,
    name character varying(255),
    id_database_type integer,
    id_database_contype integer,
    host_name character varying(255),
    database_name character varying(2000000),
    port integer,
    username character varying(255),
    password character varying(255),
    servername character varying(255),
    data_tbs character varying(255),
    index_tbs character varying(255)
);


ALTER TABLE public.r_database OWNER TO rossonet;

--
-- Name: r_database_attribute; Type: TABLE; Schema: public; Owner: rossonet
--

CREATE TABLE public.r_database_attribute (
    id_database_attribute bigint NOT NULL,
    id_database integer,
    code character varying(255),
    value_str character varying(2000000)
);


ALTER TABLE public.r_database_attribute OWNER TO rossonet;

--
-- Name: r_database_attribute_id_database_attribute_seq; Type: SEQUENCE; Schema: public; Owner: rossonet
--

CREATE SEQUENCE public.r_database_attribute_id_database_attribute_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.r_database_attribute_id_database_attribute_seq OWNER TO rossonet;

--
-- Name: r_database_attribute_id_database_attribute_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: rossonet
--

ALTER SEQUENCE public.r_database_attribute_id_database_attribute_seq OWNED BY public.r_database_attribute.id_database_attribute;


--
-- Name: r_database_contype; Type: TABLE; Schema: public; Owner: rossonet
--

CREATE TABLE public.r_database_contype (
    id_database_contype bigint NOT NULL,
    code character varying(255),
    description character varying(255)
);


ALTER TABLE public.r_database_contype OWNER TO rossonet;

--
-- Name: r_database_contype_id_database_contype_seq; Type: SEQUENCE; Schema: public; Owner: rossonet
--

CREATE SEQUENCE public.r_database_contype_id_database_contype_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.r_database_contype_id_database_contype_seq OWNER TO rossonet;

--
-- Name: r_database_contype_id_database_contype_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: rossonet
--

ALTER SEQUENCE public.r_database_contype_id_database_contype_seq OWNED BY public.r_database_contype.id_database_contype;


--
-- Name: r_database_id_database_seq; Type: SEQUENCE; Schema: public; Owner: rossonet
--

CREATE SEQUENCE public.r_database_id_database_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.r_database_id_database_seq OWNER TO rossonet;

--
-- Name: r_database_id_database_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: rossonet
--

ALTER SEQUENCE public.r_database_id_database_seq OWNED BY public.r_database.id_database;


--
-- Name: r_database_type; Type: TABLE; Schema: public; Owner: rossonet
--

CREATE TABLE public.r_database_type (
    id_database_type bigint NOT NULL,
    code character varying(255),
    description character varying(255)
);


ALTER TABLE public.r_database_type OWNER TO rossonet;

--
-- Name: r_database_type_id_database_type_seq; Type: SEQUENCE; Schema: public; Owner: rossonet
--

CREATE SEQUENCE public.r_database_type_id_database_type_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.r_database_type_id_database_type_seq OWNER TO rossonet;

--
-- Name: r_database_type_id_database_type_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: rossonet
--

ALTER SEQUENCE public.r_database_type_id_database_type_seq OWNED BY public.r_database_type.id_database_type;


--
-- Name: r_dependency; Type: TABLE; Schema: public; Owner: rossonet
--

CREATE TABLE public.r_dependency (
    id_dependency bigint NOT NULL,
    id_transformation integer,
    id_database integer,
    table_name character varying(255),
    field_name character varying(255)
);


ALTER TABLE public.r_dependency OWNER TO rossonet;

--
-- Name: r_dependency_id_dependency_seq; Type: SEQUENCE; Schema: public; Owner: rossonet
--

CREATE SEQUENCE public.r_dependency_id_dependency_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.r_dependency_id_dependency_seq OWNER TO rossonet;

--
-- Name: r_dependency_id_dependency_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: rossonet
--

ALTER SEQUENCE public.r_dependency_id_dependency_seq OWNED BY public.r_dependency.id_dependency;


--
-- Name: r_directory; Type: TABLE; Schema: public; Owner: rossonet
--

CREATE TABLE public.r_directory (
    id_directory bigint NOT NULL,
    id_directory_parent integer,
    directory_name character varying(255)
);


ALTER TABLE public.r_directory OWNER TO rossonet;

--
-- Name: r_directory_id_directory_seq; Type: SEQUENCE; Schema: public; Owner: rossonet
--

CREATE SEQUENCE public.r_directory_id_directory_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.r_directory_id_directory_seq OWNER TO rossonet;

--
-- Name: r_directory_id_directory_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: rossonet
--

ALTER SEQUENCE public.r_directory_id_directory_seq OWNED BY public.r_directory.id_directory;


--
-- Name: r_element; Type: TABLE; Schema: public; Owner: rossonet
--

CREATE TABLE public.r_element (
    id_element bigint NOT NULL,
    id_element_type integer,
    name character varying(9999998)
);


ALTER TABLE public.r_element OWNER TO rossonet;

--
-- Name: r_element_attribute; Type: TABLE; Schema: public; Owner: rossonet
--

CREATE TABLE public.r_element_attribute (
    id_element_attribute bigint NOT NULL,
    id_element integer,
    id_element_attribute_parent integer,
    attr_key character varying(255),
    attr_value character varying(2000000)
);


ALTER TABLE public.r_element_attribute OWNER TO rossonet;

--
-- Name: r_element_attribute_id_element_attribute_seq; Type: SEQUENCE; Schema: public; Owner: rossonet
--

CREATE SEQUENCE public.r_element_attribute_id_element_attribute_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.r_element_attribute_id_element_attribute_seq OWNER TO rossonet;

--
-- Name: r_element_attribute_id_element_attribute_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: rossonet
--

ALTER SEQUENCE public.r_element_attribute_id_element_attribute_seq OWNED BY public.r_element_attribute.id_element_attribute;


--
-- Name: r_element_id_element_seq; Type: SEQUENCE; Schema: public; Owner: rossonet
--

CREATE SEQUENCE public.r_element_id_element_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.r_element_id_element_seq OWNER TO rossonet;

--
-- Name: r_element_id_element_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: rossonet
--

ALTER SEQUENCE public.r_element_id_element_seq OWNED BY public.r_element.id_element;


--
-- Name: r_element_type; Type: TABLE; Schema: public; Owner: rossonet
--

CREATE TABLE public.r_element_type (
    id_element_type bigint NOT NULL,
    id_namespace integer,
    name character varying(9999998),
    description character varying(2000000)
);


ALTER TABLE public.r_element_type OWNER TO rossonet;

--
-- Name: r_element_type_id_element_type_seq; Type: SEQUENCE; Schema: public; Owner: rossonet
--

CREATE SEQUENCE public.r_element_type_id_element_type_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.r_element_type_id_element_type_seq OWNER TO rossonet;

--
-- Name: r_element_type_id_element_type_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: rossonet
--

ALTER SEQUENCE public.r_element_type_id_element_type_seq OWNED BY public.r_element_type.id_element_type;


--
-- Name: r_job; Type: TABLE; Schema: public; Owner: rossonet
--

CREATE TABLE public.r_job (
    id_job bigint NOT NULL,
    id_directory integer,
    name character varying(255),
    description character varying(2000000),
    extended_description character varying(2000000),
    job_version character varying(255),
    job_status integer,
    id_database_log integer,
    table_name_log character varying(255),
    created_user character varying(255),
    created_date timestamp without time zone,
    modified_user character varying(255),
    modified_date timestamp without time zone,
    use_batch_id boolean,
    pass_batch_id boolean,
    use_logfield boolean,
    shared_file character varying(255)
);


ALTER TABLE public.r_job OWNER TO rossonet;

--
-- Name: r_job_attribute; Type: TABLE; Schema: public; Owner: rossonet
--

CREATE TABLE public.r_job_attribute (
    id_job_attribute bigint NOT NULL,
    id_job integer,
    nr integer,
    code character varying(255),
    value_num bigint,
    value_str character varying(2000000)
);


ALTER TABLE public.r_job_attribute OWNER TO rossonet;

--
-- Name: r_job_attribute_id_job_attribute_seq; Type: SEQUENCE; Schema: public; Owner: rossonet
--

CREATE SEQUENCE public.r_job_attribute_id_job_attribute_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.r_job_attribute_id_job_attribute_seq OWNER TO rossonet;

--
-- Name: r_job_attribute_id_job_attribute_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: rossonet
--

ALTER SEQUENCE public.r_job_attribute_id_job_attribute_seq OWNED BY public.r_job_attribute.id_job_attribute;


--
-- Name: r_job_hop; Type: TABLE; Schema: public; Owner: rossonet
--

CREATE TABLE public.r_job_hop (
    id_job_hop bigint NOT NULL,
    id_job integer,
    id_jobentry_copy_from integer,
    id_jobentry_copy_to integer,
    enabled boolean,
    evaluation boolean,
    unconditional boolean
);


ALTER TABLE public.r_job_hop OWNER TO rossonet;

--
-- Name: r_job_hop_id_job_hop_seq; Type: SEQUENCE; Schema: public; Owner: rossonet
--

CREATE SEQUENCE public.r_job_hop_id_job_hop_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.r_job_hop_id_job_hop_seq OWNER TO rossonet;

--
-- Name: r_job_hop_id_job_hop_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: rossonet
--

ALTER SEQUENCE public.r_job_hop_id_job_hop_seq OWNED BY public.r_job_hop.id_job_hop;


--
-- Name: r_job_id_job_seq; Type: SEQUENCE; Schema: public; Owner: rossonet
--

CREATE SEQUENCE public.r_job_id_job_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.r_job_id_job_seq OWNER TO rossonet;

--
-- Name: r_job_id_job_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: rossonet
--

ALTER SEQUENCE public.r_job_id_job_seq OWNED BY public.r_job.id_job;


--
-- Name: r_job_lock; Type: TABLE; Schema: public; Owner: rossonet
--

CREATE TABLE public.r_job_lock (
    id_job_lock bigint NOT NULL,
    id_job integer,
    id_user integer,
    lock_message character varying(2000000),
    lock_date timestamp without time zone
);


ALTER TABLE public.r_job_lock OWNER TO rossonet;

--
-- Name: r_job_lock_id_job_lock_seq; Type: SEQUENCE; Schema: public; Owner: rossonet
--

CREATE SEQUENCE public.r_job_lock_id_job_lock_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.r_job_lock_id_job_lock_seq OWNER TO rossonet;

--
-- Name: r_job_lock_id_job_lock_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: rossonet
--

ALTER SEQUENCE public.r_job_lock_id_job_lock_seq OWNED BY public.r_job_lock.id_job_lock;


--
-- Name: r_job_note; Type: TABLE; Schema: public; Owner: rossonet
--

CREATE TABLE public.r_job_note (
    id_job integer,
    id_note integer
);


ALTER TABLE public.r_job_note OWNER TO rossonet;

--
-- Name: r_jobentry; Type: TABLE; Schema: public; Owner: rossonet
--

CREATE TABLE public.r_jobentry (
    id_jobentry bigint NOT NULL,
    id_job integer,
    id_jobentry_type integer,
    name character varying(255),
    description character varying(2000000)
);


ALTER TABLE public.r_jobentry OWNER TO rossonet;

--
-- Name: r_jobentry_attribute; Type: TABLE; Schema: public; Owner: rossonet
--

CREATE TABLE public.r_jobentry_attribute (
    id_jobentry_attribute bigint NOT NULL,
    id_job integer,
    id_jobentry integer,
    nr integer,
    code character varying(255),
    value_num numeric(15,2),
    value_str character varying(2000000)
);


ALTER TABLE public.r_jobentry_attribute OWNER TO rossonet;

--
-- Name: r_jobentry_attribute_id_jobentry_attribute_seq; Type: SEQUENCE; Schema: public; Owner: rossonet
--

CREATE SEQUENCE public.r_jobentry_attribute_id_jobentry_attribute_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.r_jobentry_attribute_id_jobentry_attribute_seq OWNER TO rossonet;

--
-- Name: r_jobentry_attribute_id_jobentry_attribute_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: rossonet
--

ALTER SEQUENCE public.r_jobentry_attribute_id_jobentry_attribute_seq OWNED BY public.r_jobentry_attribute.id_jobentry_attribute;


--
-- Name: r_jobentry_copy; Type: TABLE; Schema: public; Owner: rossonet
--

CREATE TABLE public.r_jobentry_copy (
    id_jobentry_copy bigint NOT NULL,
    id_jobentry integer,
    id_job integer,
    id_jobentry_type integer,
    nr smallint,
    gui_location_x integer,
    gui_location_y integer,
    gui_draw boolean,
    parallel boolean
);


ALTER TABLE public.r_jobentry_copy OWNER TO rossonet;

--
-- Name: r_jobentry_copy_id_jobentry_copy_seq; Type: SEQUENCE; Schema: public; Owner: rossonet
--

CREATE SEQUENCE public.r_jobentry_copy_id_jobentry_copy_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.r_jobentry_copy_id_jobentry_copy_seq OWNER TO rossonet;

--
-- Name: r_jobentry_copy_id_jobentry_copy_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: rossonet
--

ALTER SEQUENCE public.r_jobentry_copy_id_jobentry_copy_seq OWNED BY public.r_jobentry_copy.id_jobentry_copy;


--
-- Name: r_jobentry_database; Type: TABLE; Schema: public; Owner: rossonet
--

CREATE TABLE public.r_jobentry_database (
    id_job integer,
    id_jobentry integer,
    id_database integer
);


ALTER TABLE public.r_jobentry_database OWNER TO rossonet;

--
-- Name: r_jobentry_id_jobentry_seq; Type: SEQUENCE; Schema: public; Owner: rossonet
--

CREATE SEQUENCE public.r_jobentry_id_jobentry_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.r_jobentry_id_jobentry_seq OWNER TO rossonet;

--
-- Name: r_jobentry_id_jobentry_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: rossonet
--

ALTER SEQUENCE public.r_jobentry_id_jobentry_seq OWNED BY public.r_jobentry.id_jobentry;


--
-- Name: r_jobentry_type; Type: TABLE; Schema: public; Owner: rossonet
--

CREATE TABLE public.r_jobentry_type (
    id_jobentry_type bigint NOT NULL,
    code character varying(255),
    description character varying(255)
);


ALTER TABLE public.r_jobentry_type OWNER TO rossonet;

--
-- Name: r_jobentry_type_id_jobentry_type_seq; Type: SEQUENCE; Schema: public; Owner: rossonet
--

CREATE SEQUENCE public.r_jobentry_type_id_jobentry_type_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.r_jobentry_type_id_jobentry_type_seq OWNER TO rossonet;

--
-- Name: r_jobentry_type_id_jobentry_type_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: rossonet
--

ALTER SEQUENCE public.r_jobentry_type_id_jobentry_type_seq OWNED BY public.r_jobentry_type.id_jobentry_type;


--
-- Name: r_log; Type: TABLE; Schema: public; Owner: rossonet
--

CREATE TABLE public.r_log (
    id_log bigint NOT NULL,
    name character varying(255),
    id_loglevel integer,
    logtype character varying(255),
    filename character varying(255),
    fileextention character varying(255),
    add_date boolean,
    add_time boolean,
    id_database_log integer,
    table_name_log character varying(255)
);


ALTER TABLE public.r_log OWNER TO rossonet;

--
-- Name: r_log_id_log_seq; Type: SEQUENCE; Schema: public; Owner: rossonet
--

CREATE SEQUENCE public.r_log_id_log_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.r_log_id_log_seq OWNER TO rossonet;

--
-- Name: r_log_id_log_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: rossonet
--

ALTER SEQUENCE public.r_log_id_log_seq OWNED BY public.r_log.id_log;


--
-- Name: r_loglevel; Type: TABLE; Schema: public; Owner: rossonet
--

CREATE TABLE public.r_loglevel (
    id_loglevel bigint NOT NULL,
    code character varying(255),
    description character varying(255)
);


ALTER TABLE public.r_loglevel OWNER TO rossonet;

--
-- Name: r_loglevel_id_loglevel_seq; Type: SEQUENCE; Schema: public; Owner: rossonet
--

CREATE SEQUENCE public.r_loglevel_id_loglevel_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.r_loglevel_id_loglevel_seq OWNER TO rossonet;

--
-- Name: r_loglevel_id_loglevel_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: rossonet
--

ALTER SEQUENCE public.r_loglevel_id_loglevel_seq OWNED BY public.r_loglevel.id_loglevel;


--
-- Name: r_namespace; Type: TABLE; Schema: public; Owner: rossonet
--

CREATE TABLE public.r_namespace (
    id_namespace bigint NOT NULL,
    name character varying(9999998)
);


ALTER TABLE public.r_namespace OWNER TO rossonet;

--
-- Name: r_namespace_id_namespace_seq; Type: SEQUENCE; Schema: public; Owner: rossonet
--

CREATE SEQUENCE public.r_namespace_id_namespace_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.r_namespace_id_namespace_seq OWNER TO rossonet;

--
-- Name: r_namespace_id_namespace_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: rossonet
--

ALTER SEQUENCE public.r_namespace_id_namespace_seq OWNED BY public.r_namespace.id_namespace;


--
-- Name: r_note; Type: TABLE; Schema: public; Owner: rossonet
--

CREATE TABLE public.r_note (
    id_note bigint NOT NULL,
    value_str character varying(2000000),
    gui_location_x integer,
    gui_location_y integer,
    gui_location_width integer,
    gui_location_height integer,
    font_name character varying(2000000),
    font_size integer,
    font_bold boolean,
    font_italic boolean,
    font_color_red integer,
    font_color_green integer,
    font_color_blue integer,
    font_back_ground_color_red integer,
    font_back_ground_color_green integer,
    font_back_ground_color_blue integer,
    font_border_color_red integer,
    font_border_color_green integer,
    font_border_color_blue integer,
    draw_shadow boolean
);


ALTER TABLE public.r_note OWNER TO rossonet;

--
-- Name: r_note_id_note_seq; Type: SEQUENCE; Schema: public; Owner: rossonet
--

CREATE SEQUENCE public.r_note_id_note_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.r_note_id_note_seq OWNER TO rossonet;

--
-- Name: r_note_id_note_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: rossonet
--

ALTER SEQUENCE public.r_note_id_note_seq OWNED BY public.r_note.id_note;


--
-- Name: r_partition; Type: TABLE; Schema: public; Owner: rossonet
--

CREATE TABLE public.r_partition (
    id_partition bigint NOT NULL,
    id_partition_schema integer,
    partition_id character varying(255)
);


ALTER TABLE public.r_partition OWNER TO rossonet;

--
-- Name: r_partition_id_partition_seq; Type: SEQUENCE; Schema: public; Owner: rossonet
--

CREATE SEQUENCE public.r_partition_id_partition_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.r_partition_id_partition_seq OWNER TO rossonet;

--
-- Name: r_partition_id_partition_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: rossonet
--

ALTER SEQUENCE public.r_partition_id_partition_seq OWNED BY public.r_partition.id_partition;


--
-- Name: r_partition_schema; Type: TABLE; Schema: public; Owner: rossonet
--

CREATE TABLE public.r_partition_schema (
    id_partition_schema bigint NOT NULL,
    name character varying(255),
    dynamic_definition boolean,
    partitions_per_slave character varying(255)
);


ALTER TABLE public.r_partition_schema OWNER TO rossonet;

--
-- Name: r_partition_schema_id_partition_schema_seq; Type: SEQUENCE; Schema: public; Owner: rossonet
--

CREATE SEQUENCE public.r_partition_schema_id_partition_schema_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.r_partition_schema_id_partition_schema_seq OWNER TO rossonet;

--
-- Name: r_partition_schema_id_partition_schema_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: rossonet
--

ALTER SEQUENCE public.r_partition_schema_id_partition_schema_seq OWNED BY public.r_partition_schema.id_partition_schema;


--
-- Name: r_repository_log; Type: TABLE; Schema: public; Owner: rossonet
--

CREATE TABLE public.r_repository_log (
    id_repository_log bigint NOT NULL,
    rep_version character varying(255),
    log_date timestamp without time zone,
    log_user character varying(255),
    operation_desc character varying(2000000)
);


ALTER TABLE public.r_repository_log OWNER TO rossonet;

--
-- Name: r_repository_log_id_repository_log_seq; Type: SEQUENCE; Schema: public; Owner: rossonet
--

CREATE SEQUENCE public.r_repository_log_id_repository_log_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.r_repository_log_id_repository_log_seq OWNER TO rossonet;

--
-- Name: r_repository_log_id_repository_log_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: rossonet
--

ALTER SEQUENCE public.r_repository_log_id_repository_log_seq OWNED BY public.r_repository_log.id_repository_log;


--
-- Name: r_slave; Type: TABLE; Schema: public; Owner: rossonet
--

CREATE TABLE public.r_slave (
    id_slave bigint NOT NULL,
    name character varying(255),
    host_name character varying(255),
    port character varying(255),
    web_app_name character varying(255),
    username character varying(255),
    password character varying(255),
    proxy_host_name character varying(255),
    proxy_port character varying(255),
    non_proxy_hosts character varying(255),
    master boolean
);


ALTER TABLE public.r_slave OWNER TO rossonet;

--
-- Name: r_slave_id_slave_seq; Type: SEQUENCE; Schema: public; Owner: rossonet
--

CREATE SEQUENCE public.r_slave_id_slave_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.r_slave_id_slave_seq OWNER TO rossonet;

--
-- Name: r_slave_id_slave_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: rossonet
--

ALTER SEQUENCE public.r_slave_id_slave_seq OWNED BY public.r_slave.id_slave;


--
-- Name: r_step; Type: TABLE; Schema: public; Owner: rossonet
--

CREATE TABLE public.r_step (
    id_step bigint NOT NULL,
    id_transformation integer,
    name character varying(255),
    description character varying(2000000),
    id_step_type integer,
    distribute boolean,
    copies smallint,
    gui_location_x integer,
    gui_location_y integer,
    gui_draw boolean,
    copies_string character varying(255)
);


ALTER TABLE public.r_step OWNER TO rossonet;

--
-- Name: r_step_attribute; Type: TABLE; Schema: public; Owner: rossonet
--

CREATE TABLE public.r_step_attribute (
    id_step_attribute bigint NOT NULL,
    id_transformation integer,
    id_step integer,
    nr integer,
    code character varying(255),
    value_num bigint,
    value_str character varying(2000000)
);


ALTER TABLE public.r_step_attribute OWNER TO rossonet;

--
-- Name: r_step_attribute_id_step_attribute_seq; Type: SEQUENCE; Schema: public; Owner: rossonet
--

CREATE SEQUENCE public.r_step_attribute_id_step_attribute_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.r_step_attribute_id_step_attribute_seq OWNER TO rossonet;

--
-- Name: r_step_attribute_id_step_attribute_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: rossonet
--

ALTER SEQUENCE public.r_step_attribute_id_step_attribute_seq OWNED BY public.r_step_attribute.id_step_attribute;


--
-- Name: r_step_database; Type: TABLE; Schema: public; Owner: rossonet
--

CREATE TABLE public.r_step_database (
    id_transformation integer,
    id_step integer,
    id_database integer
);


ALTER TABLE public.r_step_database OWNER TO rossonet;

--
-- Name: r_step_id_step_seq; Type: SEQUENCE; Schema: public; Owner: rossonet
--

CREATE SEQUENCE public.r_step_id_step_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.r_step_id_step_seq OWNER TO rossonet;

--
-- Name: r_step_id_step_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: rossonet
--

ALTER SEQUENCE public.r_step_id_step_seq OWNED BY public.r_step.id_step;


--
-- Name: r_step_type; Type: TABLE; Schema: public; Owner: rossonet
--

CREATE TABLE public.r_step_type (
    id_step_type bigint NOT NULL,
    code character varying(255),
    description character varying(255),
    helptext character varying(255)
);


ALTER TABLE public.r_step_type OWNER TO rossonet;

--
-- Name: r_step_type_id_step_type_seq; Type: SEQUENCE; Schema: public; Owner: rossonet
--

CREATE SEQUENCE public.r_step_type_id_step_type_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.r_step_type_id_step_type_seq OWNER TO rossonet;

--
-- Name: r_step_type_id_step_type_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: rossonet
--

ALTER SEQUENCE public.r_step_type_id_step_type_seq OWNED BY public.r_step_type.id_step_type;


--
-- Name: r_trans_attribute; Type: TABLE; Schema: public; Owner: rossonet
--

CREATE TABLE public.r_trans_attribute (
    id_trans_attribute bigint NOT NULL,
    id_transformation integer,
    nr integer,
    code character varying(255),
    value_num bigint,
    value_str character varying(2000000)
);


ALTER TABLE public.r_trans_attribute OWNER TO rossonet;

--
-- Name: r_trans_attribute_id_trans_attribute_seq; Type: SEQUENCE; Schema: public; Owner: rossonet
--

CREATE SEQUENCE public.r_trans_attribute_id_trans_attribute_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.r_trans_attribute_id_trans_attribute_seq OWNER TO rossonet;

--
-- Name: r_trans_attribute_id_trans_attribute_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: rossonet
--

ALTER SEQUENCE public.r_trans_attribute_id_trans_attribute_seq OWNED BY public.r_trans_attribute.id_trans_attribute;


--
-- Name: r_trans_cluster; Type: TABLE; Schema: public; Owner: rossonet
--

CREATE TABLE public.r_trans_cluster (
    id_trans_cluster bigint NOT NULL,
    id_transformation integer,
    id_cluster integer
);


ALTER TABLE public.r_trans_cluster OWNER TO rossonet;

--
-- Name: r_trans_cluster_id_trans_cluster_seq; Type: SEQUENCE; Schema: public; Owner: rossonet
--

CREATE SEQUENCE public.r_trans_cluster_id_trans_cluster_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.r_trans_cluster_id_trans_cluster_seq OWNER TO rossonet;

--
-- Name: r_trans_cluster_id_trans_cluster_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: rossonet
--

ALTER SEQUENCE public.r_trans_cluster_id_trans_cluster_seq OWNED BY public.r_trans_cluster.id_trans_cluster;


--
-- Name: r_trans_hop; Type: TABLE; Schema: public; Owner: rossonet
--

CREATE TABLE public.r_trans_hop (
    id_trans_hop bigint NOT NULL,
    id_transformation integer,
    id_step_from integer,
    id_step_to integer,
    enabled boolean
);


ALTER TABLE public.r_trans_hop OWNER TO rossonet;

--
-- Name: r_trans_hop_id_trans_hop_seq; Type: SEQUENCE; Schema: public; Owner: rossonet
--

CREATE SEQUENCE public.r_trans_hop_id_trans_hop_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.r_trans_hop_id_trans_hop_seq OWNER TO rossonet;

--
-- Name: r_trans_hop_id_trans_hop_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: rossonet
--

ALTER SEQUENCE public.r_trans_hop_id_trans_hop_seq OWNED BY public.r_trans_hop.id_trans_hop;


--
-- Name: r_trans_lock; Type: TABLE; Schema: public; Owner: rossonet
--

CREATE TABLE public.r_trans_lock (
    id_trans_lock bigint NOT NULL,
    id_transformation integer,
    id_user integer,
    lock_message character varying(2000000),
    lock_date timestamp without time zone
);


ALTER TABLE public.r_trans_lock OWNER TO rossonet;

--
-- Name: r_trans_lock_id_trans_lock_seq; Type: SEQUENCE; Schema: public; Owner: rossonet
--

CREATE SEQUENCE public.r_trans_lock_id_trans_lock_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.r_trans_lock_id_trans_lock_seq OWNER TO rossonet;

--
-- Name: r_trans_lock_id_trans_lock_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: rossonet
--

ALTER SEQUENCE public.r_trans_lock_id_trans_lock_seq OWNED BY public.r_trans_lock.id_trans_lock;


--
-- Name: r_trans_note; Type: TABLE; Schema: public; Owner: rossonet
--

CREATE TABLE public.r_trans_note (
    id_transformation integer,
    id_note integer
);


ALTER TABLE public.r_trans_note OWNER TO rossonet;

--
-- Name: r_trans_partition_schema; Type: TABLE; Schema: public; Owner: rossonet
--

CREATE TABLE public.r_trans_partition_schema (
    id_trans_partition_schema bigint NOT NULL,
    id_transformation integer,
    id_partition_schema integer
);


ALTER TABLE public.r_trans_partition_schema OWNER TO rossonet;

--
-- Name: r_trans_partition_schema_id_trans_partition_schema_seq; Type: SEQUENCE; Schema: public; Owner: rossonet
--

CREATE SEQUENCE public.r_trans_partition_schema_id_trans_partition_schema_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.r_trans_partition_schema_id_trans_partition_schema_seq OWNER TO rossonet;

--
-- Name: r_trans_partition_schema_id_trans_partition_schema_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: rossonet
--

ALTER SEQUENCE public.r_trans_partition_schema_id_trans_partition_schema_seq OWNED BY public.r_trans_partition_schema.id_trans_partition_schema;


--
-- Name: r_trans_slave; Type: TABLE; Schema: public; Owner: rossonet
--

CREATE TABLE public.r_trans_slave (
    id_trans_slave bigint NOT NULL,
    id_transformation integer,
    id_slave integer
);


ALTER TABLE public.r_trans_slave OWNER TO rossonet;

--
-- Name: r_trans_slave_id_trans_slave_seq; Type: SEQUENCE; Schema: public; Owner: rossonet
--

CREATE SEQUENCE public.r_trans_slave_id_trans_slave_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.r_trans_slave_id_trans_slave_seq OWNER TO rossonet;

--
-- Name: r_trans_slave_id_trans_slave_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: rossonet
--

ALTER SEQUENCE public.r_trans_slave_id_trans_slave_seq OWNED BY public.r_trans_slave.id_trans_slave;


--
-- Name: r_trans_step_condition; Type: TABLE; Schema: public; Owner: rossonet
--

CREATE TABLE public.r_trans_step_condition (
    id_transformation integer,
    id_step integer,
    id_condition integer
);


ALTER TABLE public.r_trans_step_condition OWNER TO rossonet;

--
-- Name: r_transformation; Type: TABLE; Schema: public; Owner: rossonet
--

CREATE TABLE public.r_transformation (
    id_transformation bigint NOT NULL,
    id_directory integer,
    name character varying(255),
    description character varying(2000000),
    extended_description character varying(2000000),
    trans_version character varying(255),
    trans_status integer,
    id_step_read integer,
    id_step_write integer,
    id_step_input integer,
    id_step_output integer,
    id_step_update integer,
    id_database_log integer,
    table_name_log character varying(255),
    use_batchid boolean,
    use_logfield boolean,
    id_database_maxdate integer,
    table_name_maxdate character varying(255),
    field_name_maxdate character varying(255),
    offset_maxdate numeric(14,2),
    diff_maxdate numeric(14,2),
    created_user character varying(255),
    created_date timestamp without time zone,
    modified_user character varying(255),
    modified_date timestamp without time zone,
    size_rowset integer
);


ALTER TABLE public.r_transformation OWNER TO rossonet;

--
-- Name: r_transformation_id_transformation_seq; Type: SEQUENCE; Schema: public; Owner: rossonet
--

CREATE SEQUENCE public.r_transformation_id_transformation_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.r_transformation_id_transformation_seq OWNER TO rossonet;

--
-- Name: r_transformation_id_transformation_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: rossonet
--

ALTER SEQUENCE public.r_transformation_id_transformation_seq OWNED BY public.r_transformation.id_transformation;


--
-- Name: r_user; Type: TABLE; Schema: public; Owner: rossonet
--

CREATE TABLE public.r_user (
    id_user bigint NOT NULL,
    login character varying(255),
    password character varying(255),
    name character varying(255),
    description character varying(255),
    enabled boolean
);


ALTER TABLE public.r_user OWNER TO rossonet;

--
-- Name: r_user_id_user_seq; Type: SEQUENCE; Schema: public; Owner: rossonet
--

CREATE SEQUENCE public.r_user_id_user_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.r_user_id_user_seq OWNER TO rossonet;

--
-- Name: r_user_id_user_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: rossonet
--

ALTER SEQUENCE public.r_user_id_user_seq OWNED BY public.r_user.id_user;


--
-- Name: r_value; Type: TABLE; Schema: public; Owner: rossonet
--

CREATE TABLE public.r_value (
    id_value bigint NOT NULL,
    name character varying(255),
    value_type character varying(255),
    value_str character varying(255),
    is_null boolean
);


ALTER TABLE public.r_value OWNER TO rossonet;

--
-- Name: r_value_id_value_seq; Type: SEQUENCE; Schema: public; Owner: rossonet
--

CREATE SEQUENCE public.r_value_id_value_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.r_value_id_value_seq OWNER TO rossonet;

--
-- Name: r_value_id_value_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: rossonet
--

ALTER SEQUENCE public.r_value_id_value_seq OWNED BY public.r_value.id_value;


--
-- Name: r_version; Type: TABLE; Schema: public; Owner: rossonet
--

CREATE TABLE public.r_version (
    id_version bigint NOT NULL,
    major_version smallint,
    minor_version smallint,
    upgrade_date timestamp without time zone,
    is_upgrade boolean
);


ALTER TABLE public.r_version OWNER TO rossonet;

--
-- Name: r_version_id_version_seq; Type: SEQUENCE; Schema: public; Owner: rossonet
--

CREATE SEQUENCE public.r_version_id_version_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.r_version_id_version_seq OWNER TO rossonet;

--
-- Name: r_version_id_version_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: rossonet
--

ALTER SEQUENCE public.r_version_id_version_seq OWNED BY public.r_version.id_version;


--
-- Name: r_cluster id_cluster; Type: DEFAULT; Schema: public; Owner: rossonet
--

ALTER TABLE ONLY public.r_cluster ALTER COLUMN id_cluster SET DEFAULT nextval('public.r_cluster_id_cluster_seq'::regclass);


--
-- Name: r_cluster_slave id_cluster_slave; Type: DEFAULT; Schema: public; Owner: rossonet
--

ALTER TABLE ONLY public.r_cluster_slave ALTER COLUMN id_cluster_slave SET DEFAULT nextval('public.r_cluster_slave_id_cluster_slave_seq'::regclass);


--
-- Name: r_condition id_condition; Type: DEFAULT; Schema: public; Owner: rossonet
--

ALTER TABLE ONLY public.r_condition ALTER COLUMN id_condition SET DEFAULT nextval('public.r_condition_id_condition_seq'::regclass);


--
-- Name: r_database id_database; Type: DEFAULT; Schema: public; Owner: rossonet
--

ALTER TABLE ONLY public.r_database ALTER COLUMN id_database SET DEFAULT nextval('public.r_database_id_database_seq'::regclass);


--
-- Name: r_database_attribute id_database_attribute; Type: DEFAULT; Schema: public; Owner: rossonet
--

ALTER TABLE ONLY public.r_database_attribute ALTER COLUMN id_database_attribute SET DEFAULT nextval('public.r_database_attribute_id_database_attribute_seq'::regclass);


--
-- Name: r_database_contype id_database_contype; Type: DEFAULT; Schema: public; Owner: rossonet
--

ALTER TABLE ONLY public.r_database_contype ALTER COLUMN id_database_contype SET DEFAULT nextval('public.r_database_contype_id_database_contype_seq'::regclass);


--
-- Name: r_database_type id_database_type; Type: DEFAULT; Schema: public; Owner: rossonet
--

ALTER TABLE ONLY public.r_database_type ALTER COLUMN id_database_type SET DEFAULT nextval('public.r_database_type_id_database_type_seq'::regclass);


--
-- Name: r_dependency id_dependency; Type: DEFAULT; Schema: public; Owner: rossonet
--

ALTER TABLE ONLY public.r_dependency ALTER COLUMN id_dependency SET DEFAULT nextval('public.r_dependency_id_dependency_seq'::regclass);


--
-- Name: r_directory id_directory; Type: DEFAULT; Schema: public; Owner: rossonet
--

ALTER TABLE ONLY public.r_directory ALTER COLUMN id_directory SET DEFAULT nextval('public.r_directory_id_directory_seq'::regclass);


--
-- Name: r_element id_element; Type: DEFAULT; Schema: public; Owner: rossonet
--

ALTER TABLE ONLY public.r_element ALTER COLUMN id_element SET DEFAULT nextval('public.r_element_id_element_seq'::regclass);


--
-- Name: r_element_attribute id_element_attribute; Type: DEFAULT; Schema: public; Owner: rossonet
--

ALTER TABLE ONLY public.r_element_attribute ALTER COLUMN id_element_attribute SET DEFAULT nextval('public.r_element_attribute_id_element_attribute_seq'::regclass);


--
-- Name: r_element_type id_element_type; Type: DEFAULT; Schema: public; Owner: rossonet
--

ALTER TABLE ONLY public.r_element_type ALTER COLUMN id_element_type SET DEFAULT nextval('public.r_element_type_id_element_type_seq'::regclass);


--
-- Name: r_job id_job; Type: DEFAULT; Schema: public; Owner: rossonet
--

ALTER TABLE ONLY public.r_job ALTER COLUMN id_job SET DEFAULT nextval('public.r_job_id_job_seq'::regclass);


--
-- Name: r_job_attribute id_job_attribute; Type: DEFAULT; Schema: public; Owner: rossonet
--

ALTER TABLE ONLY public.r_job_attribute ALTER COLUMN id_job_attribute SET DEFAULT nextval('public.r_job_attribute_id_job_attribute_seq'::regclass);


--
-- Name: r_job_hop id_job_hop; Type: DEFAULT; Schema: public; Owner: rossonet
--

ALTER TABLE ONLY public.r_job_hop ALTER COLUMN id_job_hop SET DEFAULT nextval('public.r_job_hop_id_job_hop_seq'::regclass);


--
-- Name: r_job_lock id_job_lock; Type: DEFAULT; Schema: public; Owner: rossonet
--

ALTER TABLE ONLY public.r_job_lock ALTER COLUMN id_job_lock SET DEFAULT nextval('public.r_job_lock_id_job_lock_seq'::regclass);


--
-- Name: r_jobentry id_jobentry; Type: DEFAULT; Schema: public; Owner: rossonet
--

ALTER TABLE ONLY public.r_jobentry ALTER COLUMN id_jobentry SET DEFAULT nextval('public.r_jobentry_id_jobentry_seq'::regclass);


--
-- Name: r_jobentry_attribute id_jobentry_attribute; Type: DEFAULT; Schema: public; Owner: rossonet
--

ALTER TABLE ONLY public.r_jobentry_attribute ALTER COLUMN id_jobentry_attribute SET DEFAULT nextval('public.r_jobentry_attribute_id_jobentry_attribute_seq'::regclass);


--
-- Name: r_jobentry_copy id_jobentry_copy; Type: DEFAULT; Schema: public; Owner: rossonet
--

ALTER TABLE ONLY public.r_jobentry_copy ALTER COLUMN id_jobentry_copy SET DEFAULT nextval('public.r_jobentry_copy_id_jobentry_copy_seq'::regclass);


--
-- Name: r_jobentry_type id_jobentry_type; Type: DEFAULT; Schema: public; Owner: rossonet
--

ALTER TABLE ONLY public.r_jobentry_type ALTER COLUMN id_jobentry_type SET DEFAULT nextval('public.r_jobentry_type_id_jobentry_type_seq'::regclass);


--
-- Name: r_log id_log; Type: DEFAULT; Schema: public; Owner: rossonet
--

ALTER TABLE ONLY public.r_log ALTER COLUMN id_log SET DEFAULT nextval('public.r_log_id_log_seq'::regclass);


--
-- Name: r_loglevel id_loglevel; Type: DEFAULT; Schema: public; Owner: rossonet
--

ALTER TABLE ONLY public.r_loglevel ALTER COLUMN id_loglevel SET DEFAULT nextval('public.r_loglevel_id_loglevel_seq'::regclass);


--
-- Name: r_namespace id_namespace; Type: DEFAULT; Schema: public; Owner: rossonet
--

ALTER TABLE ONLY public.r_namespace ALTER COLUMN id_namespace SET DEFAULT nextval('public.r_namespace_id_namespace_seq'::regclass);


--
-- Name: r_note id_note; Type: DEFAULT; Schema: public; Owner: rossonet
--

ALTER TABLE ONLY public.r_note ALTER COLUMN id_note SET DEFAULT nextval('public.r_note_id_note_seq'::regclass);


--
-- Name: r_partition id_partition; Type: DEFAULT; Schema: public; Owner: rossonet
--

ALTER TABLE ONLY public.r_partition ALTER COLUMN id_partition SET DEFAULT nextval('public.r_partition_id_partition_seq'::regclass);


--
-- Name: r_partition_schema id_partition_schema; Type: DEFAULT; Schema: public; Owner: rossonet
--

ALTER TABLE ONLY public.r_partition_schema ALTER COLUMN id_partition_schema SET DEFAULT nextval('public.r_partition_schema_id_partition_schema_seq'::regclass);


--
-- Name: r_repository_log id_repository_log; Type: DEFAULT; Schema: public; Owner: rossonet
--

ALTER TABLE ONLY public.r_repository_log ALTER COLUMN id_repository_log SET DEFAULT nextval('public.r_repository_log_id_repository_log_seq'::regclass);


--
-- Name: r_slave id_slave; Type: DEFAULT; Schema: public; Owner: rossonet
--

ALTER TABLE ONLY public.r_slave ALTER COLUMN id_slave SET DEFAULT nextval('public.r_slave_id_slave_seq'::regclass);


--
-- Name: r_step id_step; Type: DEFAULT; Schema: public; Owner: rossonet
--

ALTER TABLE ONLY public.r_step ALTER COLUMN id_step SET DEFAULT nextval('public.r_step_id_step_seq'::regclass);


--
-- Name: r_step_attribute id_step_attribute; Type: DEFAULT; Schema: public; Owner: rossonet
--

ALTER TABLE ONLY public.r_step_attribute ALTER COLUMN id_step_attribute SET DEFAULT nextval('public.r_step_attribute_id_step_attribute_seq'::regclass);


--
-- Name: r_step_type id_step_type; Type: DEFAULT; Schema: public; Owner: rossonet
--

ALTER TABLE ONLY public.r_step_type ALTER COLUMN id_step_type SET DEFAULT nextval('public.r_step_type_id_step_type_seq'::regclass);


--
-- Name: r_trans_attribute id_trans_attribute; Type: DEFAULT; Schema: public; Owner: rossonet
--

ALTER TABLE ONLY public.r_trans_attribute ALTER COLUMN id_trans_attribute SET DEFAULT nextval('public.r_trans_attribute_id_trans_attribute_seq'::regclass);


--
-- Name: r_trans_cluster id_trans_cluster; Type: DEFAULT; Schema: public; Owner: rossonet
--

ALTER TABLE ONLY public.r_trans_cluster ALTER COLUMN id_trans_cluster SET DEFAULT nextval('public.r_trans_cluster_id_trans_cluster_seq'::regclass);


--
-- Name: r_trans_hop id_trans_hop; Type: DEFAULT; Schema: public; Owner: rossonet
--

ALTER TABLE ONLY public.r_trans_hop ALTER COLUMN id_trans_hop SET DEFAULT nextval('public.r_trans_hop_id_trans_hop_seq'::regclass);


--
-- Name: r_trans_lock id_trans_lock; Type: DEFAULT; Schema: public; Owner: rossonet
--

ALTER TABLE ONLY public.r_trans_lock ALTER COLUMN id_trans_lock SET DEFAULT nextval('public.r_trans_lock_id_trans_lock_seq'::regclass);


--
-- Name: r_trans_partition_schema id_trans_partition_schema; Type: DEFAULT; Schema: public; Owner: rossonet
--

ALTER TABLE ONLY public.r_trans_partition_schema ALTER COLUMN id_trans_partition_schema SET DEFAULT nextval('public.r_trans_partition_schema_id_trans_partition_schema_seq'::regclass);


--
-- Name: r_trans_slave id_trans_slave; Type: DEFAULT; Schema: public; Owner: rossonet
--

ALTER TABLE ONLY public.r_trans_slave ALTER COLUMN id_trans_slave SET DEFAULT nextval('public.r_trans_slave_id_trans_slave_seq'::regclass);


--
-- Name: r_transformation id_transformation; Type: DEFAULT; Schema: public; Owner: rossonet
--

ALTER TABLE ONLY public.r_transformation ALTER COLUMN id_transformation SET DEFAULT nextval('public.r_transformation_id_transformation_seq'::regclass);


--
-- Name: r_user id_user; Type: DEFAULT; Schema: public; Owner: rossonet
--

ALTER TABLE ONLY public.r_user ALTER COLUMN id_user SET DEFAULT nextval('public.r_user_id_user_seq'::regclass);


--
-- Name: r_value id_value; Type: DEFAULT; Schema: public; Owner: rossonet
--

ALTER TABLE ONLY public.r_value ALTER COLUMN id_value SET DEFAULT nextval('public.r_value_id_value_seq'::regclass);


--
-- Name: r_version id_version; Type: DEFAULT; Schema: public; Owner: rossonet
--

ALTER TABLE ONLY public.r_version ALTER COLUMN id_version SET DEFAULT nextval('public.r_version_id_version_seq'::regclass);


--
-- Data for Name: r_cluster; Type: TABLE DATA; Schema: public; Owner: rossonet
--

COPY public.r_cluster (id_cluster, name, base_port, sockets_buffer_size, sockets_flush_interval, sockets_compressed, dynamic_cluster) FROM stdin;
\.


--
-- Data for Name: r_cluster_slave; Type: TABLE DATA; Schema: public; Owner: rossonet
--

COPY public.r_cluster_slave (id_cluster_slave, id_cluster, id_slave) FROM stdin;
\.


--
-- Data for Name: r_condition; Type: TABLE DATA; Schema: public; Owner: rossonet
--

COPY public.r_condition (id_condition, id_condition_parent, negated, operator, left_name, condition_function, right_name, id_value_right) FROM stdin;
\.


--
-- Data for Name: r_database; Type: TABLE DATA; Schema: public; Owner: rossonet
--

COPY public.r_database (id_database, name, id_database_type, id_database_contype, host_name, database_name, port, username, password, servername, data_tbs, index_tbs) FROM stdin;
\.


--
-- Data for Name: r_database_attribute; Type: TABLE DATA; Schema: public; Owner: rossonet
--

COPY public.r_database_attribute (id_database_attribute, id_database, code, value_str) FROM stdin;
\.


--
-- Data for Name: r_database_contype; Type: TABLE DATA; Schema: public; Owner: rossonet
--

COPY public.r_database_contype (id_database_contype, code, description) FROM stdin;
1	Native	Native (JDBC)
2	ODBC	ODBC
3	OCI	OCI
4	Plugin	Plugin specific access method
5	JNDI	JNDI
6	,	Custom
\.


--
-- Data for Name: r_database_type; Type: TABLE DATA; Schema: public; Owner: rossonet
--

COPY public.r_database_type (id_database_type, code, description) FROM stdin;
1	DERBY	Apache Derby
2	AS/400	AS/400
3	INTERBASE	Borland Interbase
4	INFINIDB	Calpont InfiniDB
5	DBASE	dBase III, IV or 5
6	EXASOL4	Exasol 4
7	EXTENDB	ExtenDB
8	FIREBIRD	Firebird SQL
9	GENERIC	Generic database
10	GREENPLUM	Greenplum
11	SQLBASE	Gupta SQL Base
12	H2	H2
13	HIVE	Hadoop Hive
14	HIVE2	Hadoop Hive 2
15	HYPERSONIC	Hypersonic
16	DB2	IBM DB2
17	IMPALA	Impala
18	INFOBRIGHT	Infobright
19	INFORMIX	Informix
20	INGRES	Ingres
21	VECTORWISE	Ingres VectorWise
22	CACHE	Intersystems Cache
23	KettleThin	Kettle thin JDBC driver
24	KINGBASEES	KingbaseES
25	LucidDB	LucidDB
26	SAPDB	MaxDB (SAP DB)
27	MONETDB	MonetDB
28	MSACCESS	MS Access
29	MSSQL	MS SQL Server
30	MSSQLNATIVE	MS SQL Server (Native)
31	MYSQL	MySQL
32	MONDRIAN	Native Mondrian
33	NEOVIEW	Neoview
34	NETEZZA	Netezza
35	OpenERPDatabaseMeta	OpenERP Server
36	ORACLE	Oracle
37	ORACLERDB	Oracle RDB
38	PALO	Palo MOLAP Server
39	POSTGRESQL	PostgreSQL
40	REDSHIFT	Redshift
41	REMEDY-AR-SYSTEM	Remedy Action Request System
42	SAPR3	SAP ERP System
43	SQLITE	SQLite
44	SYBASE	Sybase
45	SYBASEIQ	SybaseIQ
46	TERADATA	Teradata
47	UNIVERSE	UniVerse database
48	VERTICA	Vertica
49	VERTICA5	Vertica 5+
\.


--
-- Data for Name: r_dependency; Type: TABLE DATA; Schema: public; Owner: rossonet
--

COPY public.r_dependency (id_dependency, id_transformation, id_database, table_name, field_name) FROM stdin;
\.


--
-- Data for Name: r_directory; Type: TABLE DATA; Schema: public; Owner: rossonet
--

COPY public.r_directory (id_directory, id_directory_parent, directory_name) FROM stdin;
\.


--
-- Data for Name: r_element; Type: TABLE DATA; Schema: public; Owner: rossonet
--

COPY public.r_element (id_element, id_element_type, name) FROM stdin;
\.


--
-- Data for Name: r_element_attribute; Type: TABLE DATA; Schema: public; Owner: rossonet
--

COPY public.r_element_attribute (id_element_attribute, id_element, id_element_attribute_parent, attr_key, attr_value) FROM stdin;
\.


--
-- Data for Name: r_element_type; Type: TABLE DATA; Schema: public; Owner: rossonet
--

COPY public.r_element_type (id_element_type, id_namespace, name, description) FROM stdin;
\.


--
-- Data for Name: r_job; Type: TABLE DATA; Schema: public; Owner: rossonet
--

COPY public.r_job (id_job, id_directory, name, description, extended_description, job_version, job_status, id_database_log, table_name_log, created_user, created_date, modified_user, modified_date, use_batch_id, pass_batch_id, use_logfield, shared_file) FROM stdin;
\.


--
-- Data for Name: r_job_attribute; Type: TABLE DATA; Schema: public; Owner: rossonet
--

COPY public.r_job_attribute (id_job_attribute, id_job, nr, code, value_num, value_str) FROM stdin;
\.


--
-- Data for Name: r_job_hop; Type: TABLE DATA; Schema: public; Owner: rossonet
--

COPY public.r_job_hop (id_job_hop, id_job, id_jobentry_copy_from, id_jobentry_copy_to, enabled, evaluation, unconditional) FROM stdin;
\.


--
-- Data for Name: r_job_lock; Type: TABLE DATA; Schema: public; Owner: rossonet
--

COPY public.r_job_lock (id_job_lock, id_job, id_user, lock_message, lock_date) FROM stdin;
\.


--
-- Data for Name: r_job_note; Type: TABLE DATA; Schema: public; Owner: rossonet
--

COPY public.r_job_note (id_job, id_note) FROM stdin;
\.


--
-- Data for Name: r_jobentry; Type: TABLE DATA; Schema: public; Owner: rossonet
--

COPY public.r_jobentry (id_jobentry, id_job, id_jobentry_type, name, description) FROM stdin;
\.


--
-- Data for Name: r_jobentry_attribute; Type: TABLE DATA; Schema: public; Owner: rossonet
--

COPY public.r_jobentry_attribute (id_jobentry_attribute, id_job, id_jobentry, nr, code, value_num, value_str) FROM stdin;
\.


--
-- Data for Name: r_jobentry_copy; Type: TABLE DATA; Schema: public; Owner: rossonet
--

COPY public.r_jobentry_copy (id_jobentry_copy, id_jobentry, id_job, id_jobentry_type, nr, gui_location_x, gui_location_y, gui_draw, parallel) FROM stdin;
\.


--
-- Data for Name: r_jobentry_database; Type: TABLE DATA; Schema: public; Owner: rossonet
--

COPY public.r_jobentry_database (id_job, id_jobentry, id_database) FROM stdin;
\.


--
-- Data for Name: r_jobentry_type; Type: TABLE DATA; Schema: public; Owner: rossonet
--

COPY public.r_jobentry_type (id_jobentry_type, code, description) FROM stdin;
1	ADD_RESULT_FILENAMES	Aggiungi i nomi file al risultato
2	EMRJobExecutorPlugin	Amazon EMR Job Executor
3	HiveJobExecutorPlugin	Amazon Hive Job Executor
4	DELAY	Aspetta
5	WAIT_FOR_FILE	Aspetta il file
6	WAIT_FOR_SQL	Aspetta SQL
7	MYSQL_BULK_FILE	BulkLoad da Mysql nel file
8	MSSQL_BULK_LOAD	BulkLoad in MSSQL
9	MYSQL_BULK_LOAD	BulkLoad in Mysql
10	DELETE_FOLDERS	Cancella cartelle
11	DELETE_FILE	Cancella file
12	FTP_DELETE	Cancella file da FTP
13	DELETE_FILES	Cancella i file
14	DELETE_RESULT_FILENAMES	Cancella i nomi file dal risultato
15	FTP_PUT	Carica file con FTP
16	SFTPPUT	Carica file con SFTP
17	CHECK_DB_CONNECTIONS	Check Db connections
18	CHECK_FILES_LOCKED	Check files locked
19	WEBSERVICE_AVAILABLE	Check webservice availability
20	FILE_COMPARE	Compara file
21	FOLDERS_COMPARE	Compara le cartelle
22	FILES_EXIST	Controlla se il file esiste
23	XML_WELL_FORMED	Controlla se il file XML è ben formato
24	FOLDER_IS_EMPTY	Controlla se la cartella è vuota
25	CONNECTED_TO_REPOSITORY	Controlla se si è connessi al repository
26	DOS_UNIX_CONVERTER	Convert file between Windows and Unix
27	COPY_FILES	Copia i file
28	COPY_MOVE_RESULT_FILENAMES	Copia o sposta il nome file del risultato
29	CREATE_FILE	Crea file
30	CREATE_FOLDER	Crea una cartella
31	PGP_DECRYPT_FILES	Decrypt files with PGP
32	PGP_ENCRYPT_FILES	Encrypt files with PGP
33	SPECIAL	Entry speciali
34	EXPORT_REPOSITORY	Esporta il repository in un file XML
35	EVAL_FILES_METRICS	Evaluate files metrics
36	DummyJob	Example plugin
37	DataCleanerJobEntry	Execute DataCleaner job
38	ZIP_FILE	File Zip
39	FTPS_GET	Get a file with FTPS
40	HadoopCopyFilesPlugin	Hadoop Copy Files
41	HadoopJobExecutorPlugin	Hadoop Job Executor 
42	HL7MLLPAcknowledge	HL7 MLLP Acknowledge
43	HL7MLLPInput	HL7 MLLP Input
44	HTTP	HTTP
45	FILE_EXISTS	Il file esiste
46	SET_VARIABLES	Imposta variabili
47	ABORT	Interrompi job
48	SNMP_TRAP	Invio SNMP trap
49	EVAL	JavaScript
50	JOB	Job
51	TABLE_EXISTS	La tabella esiste
52	COLUMNS_EXIST	Le colonne esistono in una tabella
53	MAIL	Mail
54	MS_ACCESS_BULK_LOAD	MS Access Bulk Load
55	OozieJobExecutor	Oozie Job Executor
56	PALO_CUBE_CREATE	Palo Cube Create
57	PALO_CUBE_DELETE	Palo Cube Delete
58	HadoopTransJobExecutorPlugin	Pentaho MapReduce
59	HadoopPigScriptExecutorPlugin	Pig Script Executor
60	PING	Ping su host
61	GET_POP	Preleva email dal POP
62	FTP	Preleva file con FTP
63	SFTP	Preleva file con SFTP
64	UNZIP	Scompatta file
65	WRITE_TO_LOG	Scrivi nel log
66	SYSLOG	Send information using Syslog
67	SEND_NAGIOS_PASSIVE_CHECK	Send Nagios passive check
68	SHELL	Shell
69	SparkSubmit	Spark Submit
70	MOVE_FILES	Sposta i file
71	SQL	SQL
72	SqoopExport	Sqoop Export
73	SqoopImport	Sqoop Import
74	SSH2_GET	SSH2 Get
75	SSH2_PUT	SSH2 Put
76	SUCCESS	Successo
77	TALEND_JOB_EXEC	Talend Job Execution
78	TELNET	Telnet a host
79	TRANS	Trasformazione
80	XSLT	Trasformazione XSL
81	TRUNCATE_TABLES	Tronca tabelle
82	FTPS_PUT	Upload files to FTPS
83	DTD_VALIDATOR	Validatore DTD
84	MAIL_VALIDATOR	Validatore email
85	XSD_VALIDATOR	Validatore XSD
86	EVAL_TABLE_CONTENT	Valuta il numero di righe nella tabella
87	SIMPLE_EVAL	Valutazione semplice
88	PGP_VERIFY_FILES	Verify file signature with PGP
89	MSGBOX_INFO	Visualizza informazioni Msgbox 
90	WRITE_TO_FILE	Write to file
\.


--
-- Data for Name: r_log; Type: TABLE DATA; Schema: public; Owner: rossonet
--

COPY public.r_log (id_log, name, id_loglevel, logtype, filename, fileextention, add_date, add_time, id_database_log, table_name_log) FROM stdin;
\.


--
-- Data for Name: r_loglevel; Type: TABLE DATA; Schema: public; Owner: rossonet
--

COPY public.r_loglevel (id_loglevel, code, description) FROM stdin;
1	Error	Log solo per gli errori
2	Minimal	Log minimo
3	Basic	Log di base
4	Detailed	Log dettagliato
5	Debug	Debugging
6	Rowlevel	Basso livello (molto dettagliato)
\.


--
-- Data for Name: r_namespace; Type: TABLE DATA; Schema: public; Owner: rossonet
--

COPY public.r_namespace (id_namespace, name) FROM stdin;
\.


--
-- Data for Name: r_note; Type: TABLE DATA; Schema: public; Owner: rossonet
--

COPY public.r_note (id_note, value_str, gui_location_x, gui_location_y, gui_location_width, gui_location_height, font_name, font_size, font_bold, font_italic, font_color_red, font_color_green, font_color_blue, font_back_ground_color_red, font_back_ground_color_green, font_back_ground_color_blue, font_border_color_red, font_border_color_green, font_border_color_blue, draw_shadow) FROM stdin;
\.


--
-- Data for Name: r_partition; Type: TABLE DATA; Schema: public; Owner: rossonet
--

COPY public.r_partition (id_partition, id_partition_schema, partition_id) FROM stdin;
\.


--
-- Data for Name: r_partition_schema; Type: TABLE DATA; Schema: public; Owner: rossonet
--

COPY public.r_partition_schema (id_partition_schema, name, dynamic_definition, partitions_per_slave) FROM stdin;
\.


--
-- Data for Name: r_repository_log; Type: TABLE DATA; Schema: public; Owner: rossonet
--

COPY public.r_repository_log (id_repository_log, rep_version, log_date, log_user, operation_desc) FROM stdin;
1	5.0	2022-07-19 18:04:35.769	admin	Creation of the Kettle repository
\.


--
-- Data for Name: r_slave; Type: TABLE DATA; Schema: public; Owner: rossonet
--

COPY public.r_slave (id_slave, name, host_name, port, web_app_name, username, password, proxy_host_name, proxy_port, non_proxy_hosts, master) FROM stdin;
\.


--
-- Data for Name: r_step; Type: TABLE DATA; Schema: public; Owner: rossonet
--

COPY public.r_step (id_step, id_transformation, name, description, id_step_type, distribute, copies, gui_location_x, gui_location_y, gui_draw, copies_string) FROM stdin;
\.


--
-- Data for Name: r_step_attribute; Type: TABLE DATA; Schema: public; Owner: rossonet
--

COPY public.r_step_attribute (id_step_attribute, id_transformation, id_step, nr, code, value_num, value_str) FROM stdin;
\.


--
-- Data for Name: r_step_database; Type: TABLE DATA; Schema: public; Owner: rossonet
--

COPY public.r_step_database (id_transformation, id_step, id_database) FROM stdin;
\.


--
-- Data for Name: r_step_type; Type: TABLE DATA; Schema: public; Owner: rossonet
--

COPY public.r_step_type (id_step_type, code, description, helptext) FROM stdin;
1	Abort	Abort	Abort a transformation
2	CheckSum	Add a checksum	Add a checksum column for each input row
3	Constant	Add constants	Add one or more constants to the input rows
4	Sequence	Add sequence	Get the next value from an sequence
5	FieldsChangeSequence	Add value fields changing sequence	Add sequence depending of fields value change.\nEach time value of at least one field change, PDI will reset sequence. 
6	AddXML	Add XML	Encode several fields into an XML fragment
7	AggregateRows	Aggregate Rows	This step type allows you to aggregate rows.\nIt can't do groupings.
8	AnalyticQuery	Analytic Query	Execute analytic queries over a sorted dataset (LEAD/LAG/FIRST/LAST)
9	Append	Append streams	Append 2 streams in an ordered way
10	AutoDoc	Automatic Documentation Output	This step automatically generates documentation based on input in the form of a list of transformations and jobs
11	AvroInput	Avro Input	Reads data from an Avro file
12	BlockUntilStepsFinish	Block this step until steps finish	Block this step until selected steps finish.
13	BlockingStep	Blocking Step	This step blocks until all incoming rows have been processed.  Subsequent steps only recieve the last input row to this step.
14	Calculator	Calculator	Create new fields by performing simple calculations
15	DBProc	Call DB Procedure	Get back information by calling a database procedure.
16	CallEndpointStep	Call endpoint	Call an endpoint of the BA Server.
17	CassandraInput	Cassandra Input	Reads data from a Cassandra table
18	CassandraOutput	Cassandra Output	Writes to a Cassandra table
19	ChangeFileEncoding	Change file encoding	Change file encoding and create a new file
20	ColumnExists	Check if a column exists	Check if a column exists in a table on a specified connection.
21	FileLocked	Check if file is locked	Check if a file is locked by another process
22	WebServiceAvailable	Check if webservice is available	Check if a webservice is available
23	CloneRow	Clone row	Clone a row as many times as needed
24	ClosureGenerator	Closure Generator	This step allows you to generates a closure table using parent-child relationships.
25	CombinationLookup	Combination lookup/update	Update a junk dimension in a data warehouse.\nAlternatively, look up information in this dimension.\nThe primary key of a junk dimension are all the fields.
26	ConcatFields	Concat Fields	Concat fields together into a new field (similar to the Text File Output step)
27	RowsToResult	Copy rows to result	Use this step to write rows to the executing job.\nThe information will then be passed to the next entry in this job.
28	CouchDbInput	CouchDb Input	Reads from a Couch DB view
29	CreditCardValidator	Credit card validator	The Credit card validator step will help you tell:\n(1) if a credit card number is valid (uses LUHN10 (MOD-10) algorithm)\n(2) which credit card vendor handles that number\n(VISA, MasterCard, Diners Club, EnRoute, American Express (AMEX),...)
30	CsvInput	CSV file input	Simple CSV file input
31	DataGrid	Data Grid	Enter rows of static data in a grid, usually for testing, reference or demo purpose
32	Validator	Data Validator	Validates passing data based on a set of rules
33	DBJoin	Database join	Execute a database query using stream values as parameters
34	DBLookup	Database lookup	Look up values in a database using field values
35	CubeInput	De-serialize from file	Read rows of data from a data cube.
36	Delay	Delay row	Output each input row after a delay
37	Delete	Delete	Delete data in a database table based upon keys
38	DetectEmptyStream	Detect empty stream	This step will output one empty row if input stream is empty\n(ie when input stream does not contain any row)
39	DimensionLookup	Dimension lookup/update	Update a slowly changing dimension in a data warehouse.\nAlternatively, look up information in this dimension.
40	Dummy	Dummy (do nothing)	This step type doesn't do anything.\nIt's useful however when testing things or in certain situations where you want to split streams.
41	DynamicSQLRow	Dynamic SQL row	Execute dynamic SQL statement build in a previous field
42	TypeExitEdi2XmlStep	Edi to XML	Converts Edi text to generic XML
43	ElasticSearchBulk	ElasticSearch Bulk Insert	Performs bulk inserts into ElasticSearch
44	MailInput	Email messages input	Read POP3/IMAP server and retrieve messages
45	ShapeFileReader	ESRI Shapefile Reader	Reads shape file data from an ESRI shape file and linked DBF file
46	MetaInject	ETL Metadata Injection	This step allows you to inject metadata into an existing transformation prior to execution.  This allows for the creation of dynamic and highly flexible data integration solutions.
47	DummyPlugin	Example plugin	This is an example for a plugin test step
48	ExecProcess	Execute a process	Execute a process and return the result
49	ExecSQLRow	Execute row SQL script	Execute SQL script extracted from a field\ncreated in a previous step.
50	ExecSQL	Execute SQL script	Execute an SQL script, optionally parameterized using input rows
51	FileExists	File exists	Check if a file exists
52	FilterRows	Filter rows	Filter rows using simple equations
53	FixedInput	Fixed file input	Fixed file input
54	Formula	Formula	Calcolare una formula usando libformula di Pentaho
55	FuzzyMatch	Fuzzy match	Finding approximate matches to a string using matching algorithms.\nRead a field from a main stream and output approximative value from lookup stream.
56	RandomCCNumberGenerator	Generate random credit card numbers	Generate random valide (luhn check) credit card numbers
57	RandomValue	Generate random value	Generate random value
58	RowGenerator	Generate Rows	Generate a number of empty or equal rows.
59	getXMLData	Get data from XML	Get data from XML file by using XPath.\n This step also allows you to parse XML defined in a previous field.
60	GetFileNames	Get File Names	Get file names from the operating system and send them to the next step.
61	FilesFromResult	Get files from result	This step allows you to read filenames used or generated in a previous entry in a job.
62	GetFilesRowsCount	Get Files Rows Count	Returns rows count for text files.
63	GetSlaveSequence	Get ID from slave server	Retrieves unique IDs in blocks from a slave server.  The referenced sequence needs to be configured on the slave server in the XML configuration file.
64	GetPreviousRowField	Get previous row fields	Get fields value of previous row.
65	GetRepositoryNames	Get repository names	Lists detailed information about transformations and/or jobs in a repository
66	RowsFromResult	Get rows from result	This allows you to read rows from a previous entry in a job.
67	GetSessionVariableStep	Get session variables	Get session variables from the current user session.
68	GetSubFolders	Get SubFolder names	Read a parent folder and return all subfolders
69	SystemInfo	Get System Info	Get information from the system like system date, arguments, etc.
70	GetTableNames	Get table names	Get table names from database connection and send them to the next step
71	GetVariable	Get Variables	Determine the values of certain (environment or Kettle) variables and put them in field values.
72	TypeExitGoogleAnalyticsInputStep	Google Analytics	Fetches data from google analytics account
73	GPBulkLoader	Greenplum Bulk Loader	Greenplum Bulk Loader
74	GPLoad	Greenplum Load	Greenplum Load
75	GroupBy	Group by	Builds aggregates in a group by fashion.\nThis works only on a sorted input.\nIf the input is not sorted, only double consecutive rows are handled correctly.
76	ParallelGzipCsvInput	GZIP CSV Input	Parallel GZIP CSV file input reader
77	HadoopFileInputPlugin	Hadoop File Input	Process files from an HDFS location
78	HadoopFileOutputPlugin	Hadoop File Output	Create files in an HDFS location 
79	HBaseInput	HBase Input	Reads data from a HBase table according to a mapping 
80	HBaseOutput	HBase Output	Writes data to an HBase table according to a mapping
81	HBaseRowDecoder	HBase Row Decoder	Decodes an incoming key and HBase result object according to a mapping 
82	HL7Input	HL7 Input	Reads and parses HL7 messages and outputs a series of values from the messages
83	HTTP	HTTP Client	Call a web service over HTTP by supplying a base URL by allowing parameters to be set dynamically
84	HTTPPOST	HTTP Post	Call a web service request over HTTP by supplying a base URL by allowing parameters to be set dynamically
85	DetectLastRow	Identify last row in a stream	Last row will be marked
86	IfNull	If field value is null	Sets a field value to a constant if it is null.
87	InfobrightOutput	Infobright Loader	Load data to an Infobright database table
88	VectorWiseBulkLoader	Ingres VectorWise Bulk Loader	This step interfaces with the Ingres VectorWise Bulk Loader "COPY TABLE" command.
89	Injector	Injector	Injector step to allow to inject rows into the transformation through the java API
90	InsertUpdate	Insert / Update	Update or insert rows in a database based upon keys.
91	JavaFilter	Java Filter	Filter rows using java code
92	JobExecutor	Job Executor	This step executes a Pentaho Data Integration job, sets parameters and passes rows.
93	JoinRows	Join Rows (cartesian product)	The output of this step is the cartesian product of the input streams.\nThe number of rows is the multiplication of the number of rows in the input streams.
94	JsonInput	Json Input	Extract relevant portions out of JSON structures (file or incoming field) and output rows
95	JsonOutput	Json output	Create Json bloc and output it in a field ou a file.
96	LDAPInput	LDAP Input	Read data from LDAP host
97	LDAPOutput	LDAP Output	Perform Insert, upsert, update, add or delete operations on records based on their DN (Distinguished  Name).
98	LDIFInput	LDIF Input	Read data from LDIF files
99	LoadFileInput	Load file content in memory	Load file content in memory
100	LucidDBBulkLoader	LucidDB Bulk Loader	Load data into LucidDB by using their bulk load command in streaming mode. (Doesnt work on Windows!)
101	LucidDBStreamingLoader	LucidDB Streaming Loader	Load data into LucidDB by using Remote Rows UDX.
102	Mail	Mail	Send eMail.
103	MailValidator	Mail Validator	Check if an email address is valid.
104	Mapping	Mapping (sub-transformation)	Run a mapping (sub-transformation), use MappingInput and MappingOutput to specify the fields interface
105	MappingInput	Mapping input specification	Specify the input interface of a mapping
106	MappingOutput	Mapping output specification	Specify the output interface of a mapping
107	HadoopEnterPlugin	MapReduce Input	Enter a Hadoop Mapper or Reducer transformation
108	HadoopExitPlugin	MapReduce Output	Exit a Hadoop Mapper or Reducer transformation 
109	MemoryGroupBy	Memory Group by	Builds aggregates in a group by fashion.\nThis step doesn't require sorted input.
110	MergeJoin	Merge Join	Joins two streams on a given key and outputs a joined set. The input streams must be sorted on the join key
111	MergeRows	Merge Rows (diff)	Merge two streams of rows, sorted on a certain key.  The two streams are compared and the equals, changed, deleted and new rows are flagged.
112	StepMetastructure	Metadata structure of stream	This is a step to read the metadata of the incoming stream.
113	AccessInput	Microsoft Access Input	Read data from a Microsoft Access file
114	AccessOutput	Microsoft Access Output	Stores records into an MS-Access database table.
115	ExcelInput	Microsoft Excel Input	Read data from Excel and OpenOffice Workbooks (XLS, XLSX, ODS).
116	ExcelOutput	Microsoft Excel Output	Stores records into an Excel (XLS) document with formatting information.
117	TypeExitExcelWriterStep	Microsoft Excel Writer	Writes or appends data to an Excel file
118	ScriptValueMod	Modified Java Script Value	This is a modified plugin for the Scripting Values with improved interface and performance.\nWritten & donated to open source by Martin Lange, Proconis : http://www.proconis.de
119	MondrianInput	Mondrian Input	Execute and retrieve data using an MDX query against a Pentaho Analyses OLAP server (Mondrian)
120	MonetDBAgileMart	MonetDB Agile Mart	Load data into MonetDB for Agile BI use cases
121	MonetDBBulkLoader	MonetDB Bulk Loader	Load data into MonetDB by using their bulk load command in streaming mode.
122	MongoDbInput	MongoDB Input	Reads from a Mongo DB collection
123	MongoDbOutput	MongoDB Output	Writes to a Mongo DB collection
124	MultiwayMergeJoin	Multiway Merge Join	Multiway Merge Join
125	MySQLBulkLoader	MySQL Bulk Loader	MySQL bulk loader step, loading data over a named pipe (not available on MS Windows)
126	NullIf	Null if...	Sets a field value to null if it is equal to a constant value
127	NumberRange	Number range	Create ranges based on numeric field
128	OlapInput	OLAP Input	Execute and retrieve data using an MDX query against any XML/A OLAP datasource using olap4j
129	OpenERPObjectDelete	OpenERP Object Delete	Deletes OpenERP objects
130	OpenERPObjectInput	OpenERP Object Input	Reads data from OpenERP objects
131	OpenERPObjectOutputImport	OpenERP Object Output	Writes data into OpenERP objects using the object import procedure
132	OraBulkLoader	Oracle Bulk Loader	Use Oracle Bulk Loader to load data
133	StepsMetrics	Output steps metrics	Return metrics for one or several steps
134	PaloCellInput	Palo Cell Input	Reads data from a defined Palo Cube 
135	PaloCellOutput	Palo Cell Output	Writes data to a defined Palo Cube
136	PaloDimInput	Palo Dim Input	Reads data from a defined Palo Dimension
137	PaloDimOutput	Palo Dim Output	Writes data to defined Palo Dimension
138	PentahoReportingOutput	Pentaho Reporting Output	Executes an existing report (PRPT)
139	PGPDecryptStream	PGP Decrypt stream	Decrypt data stream with PGP
140	PGPEncryptStream	PGP Encrypt stream	Encrypt data stream with PGP
141	PGBulkLoader	PostgreSQL Bulk Loader	PostgreSQL Bulk Loader
142	PrioritizeStreams	Prioritize streams	Prioritize streams in an order way.
143	ProcessFiles	Process files	Process one file per row (copy or move or delete).\nThis step only accept filename in input.
144	PropertyOutput	Properties Output	Write data to properties file
145	PropertyInput	Property Input	Read data (key, value) from properties files.
146	RegexEval	Regex Evaluation	Regular expression Evaluation\nThis step uses a regular expression to evaluate a field. It can also extract new fields out of an existing field with capturing groups.
147	ReplaceString	Replace in string	Replace all occurences a word in a string with another word.
148	ReservoirSampling	Reservoir Sampling	[Transform] Samples a fixed number of rows from the incoming stream
149	Rest	REST Client	Consume RESTfull services.\nREpresentational State Transfer (REST) is a key design idiom that embraces a stateless client-server\narchitecture in which the web services are viewed as resources and can be identified by their URLs
150	WebServiceLookup	Ricerca web service	Ricerca informazioni usando i web service (WSDL)
151	Denormaliser	Row denormaliser	Denormalises rows by looking up key-value pairs and by assigning them to new fields in the output rows.\nThis method aggregates and needs the input rows to be sorted on the grouping fields
152	Flattener	Row flattener	Flattens consecutive rows based on the order in which they appear in the input stream
153	Normaliser	Row Normaliser	De-normalised information can be normalised using this step type.
154	RssInput	RSS Input	Read RSS feeds
155	RssOutput	RSS Output	Read RSS stream.
156	RuleAccumulator	Rule Accumulator	Execute a rule against a set of all incoming rows
157	RuleExecutor	Rule Executor	Execute a rule against each row or a set of rows
158	SSH	Run SSH commands	Run SSH commands and returns result.
159	S3CSVINPUT	S3 CSV Input	S3 CSV Input
160	S3FileOutputPlugin	S3 File Output	Create files in an S3 location
161	SalesforceDelete	Salesforce Delete	Delete records in Salesforce module.
162	SalesforceInput	Salesforce Input	!BaseStep.TypeTooltipDesc.SalesforceInput!
163	SalesforceInsert	Salesforce Insert	Insert records in Salesforce module.
164	SalesforceUpdate	Salesforce Update	Update records in Salesforce module.
165	SalesforceUpsert	Salesforce Upsert	Insert or update records in Salesforce module.
166	SampleRows	Sample rows	Filter rows based on the line number.
167	SapInput	SAP Input	Read data from SAP ERP, optionally with parameters
168	SASInput	SAS Input	This step reads files in sas7bdat (SAS) native format
169	Script	Script	Calculate values by scripting in Ruby, Python, Groovy, JavaScript, ... (JSR-223)
170	SecretKeyGenerator	Secret key generator	Generate secret key for algorithms such as DES, AES, TripleDES.
171	SelectValues	Select values	Select or remove fields in a row.\nOptionally, set the field meta-data: type, length and precision.
172	SyslogMessage	Send message to Syslog	Send message to Syslog server
173	CubeOutput	Serialize to file	Write rows of data to a data cube
174	SetValueField	Set field value	Set value of a field with another value field
175	SetValueConstant	Set field value to a constant	Set value of a field to a constant
176	FilesToResult	Set files in result	This step allows you to set filenames in the result of this transformation.\nSubsequent job entries can then use this information.
177	SetSessionVariableStep	Set session variables	Set session variables in the current user session.
178	SetVariable	Set Variables	Set environment variables based on a single input row.
179	SFTPPut	SFTP Put	Upload a file or a stream file to remote host via SFTP
180	SimpleMapping	Simple Mapping (sub-transformation)	Run a mapping (sub-transformation), use MappingInput and MappingOutput to specify the fields interface.  This is the simplified version only allowing one input and one output data set.
181	SingleThreader	Single Threader	Executes a transformation snippet in a single thread.  You need a standard mapping or a transformation with an Injector step where data from the parent transformation will arive in blocks.
182	SocketReader	Socket reader	Socket reader.  A socket client that connects to a server (Socket Writer step).
183	SocketWriter	Socket writer	Socket writer.  A socket server that can send rows of data to a socket reader.
184	SortRows	Sort rows	Sort rows based upon field values (ascending or descending)
185	SortedMerge	Sorted Merge	Sorted Merge
186	SplitFieldToRows3	Split field to rows	Splits a single string field by delimiter and creates a new row for each split term
187	FieldSplitter	Split Fields	When you want to split a single field into more then one, use this step type.
188	SQLFileOutput	SQL File Output	Output SQL INSERT statements to file
189	SSTableOutput	SSTable Output	Writes to a filesystem directory as a Cassandra SSTable
190	StreamLookup	Stream lookup	Look up values coming from another stream in the transformation.
191	XMLInputSax	Streaming XML Input	Read data from an XML file in a streaming fashing, working faster and consuming less memory
192	StringOperations	String operations	Apply certain operations like trimming, padding and others to string value.
193	StringCut	Strings cut	Strings cut (substring).
194	SwitchCase	Switch / Case	Switch a row to a certain target step based on the case value in a field.
195	SymmetricCryptoTrans	Symmetric Cryptography	Encrypt or decrypt a string using symmetric encryption.\nAvailable algorithms are DES, AES, TripleDES.
196	SynchronizeAfterMerge	Synchronize after merge	This step perform insert/update/delete in one go based on the value of a field. 
197	TableAgileMart	Table Agile Mart	Load data into a table for Agile BI use cases
198	TableCompare	Table Compare	Compares 2 tables and gives back a list of differences
199	TableExists	Table exists	Check if a table exists on a specified connection
200	TableInput	Table input	Read information from a database table.
201	TableOutput	Table output	Write information to a database table
202	TeraFast	Teradata Fastload Bulk Loader	The Teradata Fastload Bulk loader
203	TeraDataBulkLoader	Teradata TPT Bulk Loader	Teradata TPT bulkloader, using tbuild command
204	TextFileInput	Text file input	Read data from a text file in several formats.\nThis data can then be passed on to the next step(s)...
205	TextFileOutput	Text file output	Write rows to a text file.
206	TransExecutor	Transformation Executor	This step executes a Pentaho Data Integration transformation, sets parameters and passes rows.
207	Unique	Unique rows	Remove double rows and leave only unique occurrences.\nThis works only on a sorted input.\nIf the input is not sorted, only double consecutive rows are handled correctly.
208	UniqueRowsByHashSet	Unique rows (HashSet)	Remove double rows and leave only unique occurrences by using a HashSet.
209	UnivariateStats	Univariate Statistics	This step computes some simple stats based on a single input field
210	Update	Update	Update data in a database table based upon keys
211	UserDefinedJavaClass	User Defined Java Class	This step allows you to program a step using Java code
212	Janino	User Defined Java Expression	Calculate the result of a Java Expression using Janino
213	ValueMapper	Value Mapper	Maps values of a certain field from one value to another
214	WriteToLog	Write to log	Write data to log
215	XBaseInput	XBase input	Reads records from an XBase type of database file (DBF)
216	XMLInput	XML Input	Read data from an XML file
217	XMLInputStream	XML Input Stream (StAX)	This step is capable of processing very large and complex XML files very fast.
218	XMLJoin	XML Join	Joins a stream of XML-Tags into a target XML string
219	XMLOutput	XML Output	Write data to an XML file
220	XSDValidator	XSD Validator	Validate XML source (files or streams) against XML Schema Definition.
221	XSLT	XSL Transformation	Transform XML stream using XSL (eXtensible Stylesheet Language).
222	YamlInput	Yaml Input 	Read YAML source (file or stream) parse them and convert them to rows and writes these to one or more output. 
223	ZipFile	Zip file	Zip a file.\nFilename will be extracted from incoming stream.
\.


--
-- Data for Name: r_trans_attribute; Type: TABLE DATA; Schema: public; Owner: rossonet
--

COPY public.r_trans_attribute (id_trans_attribute, id_transformation, nr, code, value_num, value_str) FROM stdin;
\.


--
-- Data for Name: r_trans_cluster; Type: TABLE DATA; Schema: public; Owner: rossonet
--

COPY public.r_trans_cluster (id_trans_cluster, id_transformation, id_cluster) FROM stdin;
\.


--
-- Data for Name: r_trans_hop; Type: TABLE DATA; Schema: public; Owner: rossonet
--

COPY public.r_trans_hop (id_trans_hop, id_transformation, id_step_from, id_step_to, enabled) FROM stdin;
\.


--
-- Data for Name: r_trans_lock; Type: TABLE DATA; Schema: public; Owner: rossonet
--

COPY public.r_trans_lock (id_trans_lock, id_transformation, id_user, lock_message, lock_date) FROM stdin;
\.


--
-- Data for Name: r_trans_note; Type: TABLE DATA; Schema: public; Owner: rossonet
--

COPY public.r_trans_note (id_transformation, id_note) FROM stdin;
\.


--
-- Data for Name: r_trans_partition_schema; Type: TABLE DATA; Schema: public; Owner: rossonet
--

COPY public.r_trans_partition_schema (id_trans_partition_schema, id_transformation, id_partition_schema) FROM stdin;
\.


--
-- Data for Name: r_trans_slave; Type: TABLE DATA; Schema: public; Owner: rossonet
--

COPY public.r_trans_slave (id_trans_slave, id_transformation, id_slave) FROM stdin;
\.


--
-- Data for Name: r_trans_step_condition; Type: TABLE DATA; Schema: public; Owner: rossonet
--

COPY public.r_trans_step_condition (id_transformation, id_step, id_condition) FROM stdin;
\.


--
-- Data for Name: r_transformation; Type: TABLE DATA; Schema: public; Owner: rossonet
--

COPY public.r_transformation (id_transformation, id_directory, name, description, extended_description, trans_version, trans_status, id_step_read, id_step_write, id_step_input, id_step_output, id_step_update, id_database_log, table_name_log, use_batchid, use_logfield, id_database_maxdate, table_name_maxdate, field_name_maxdate, offset_maxdate, diff_maxdate, created_user, created_date, modified_user, modified_date, size_rowset) FROM stdin;
\.


--
-- Data for Name: r_user; Type: TABLE DATA; Schema: public; Owner: rossonet
--

COPY public.r_user (id_user, login, password, name, description, enabled) FROM stdin;
1	admin	2be98afc86aa7f2e4cb79ce71da9fa6d4	Administrator	User manager	t
2	guest	2be98afc86aa7f2e4cb79ce77cb97bcce	Guest account	Read-only guest account	t
\.


--
-- Data for Name: r_value; Type: TABLE DATA; Schema: public; Owner: rossonet
--

COPY public.r_value (id_value, name, value_type, value_str, is_null) FROM stdin;
\.


--
-- Data for Name: r_version; Type: TABLE DATA; Schema: public; Owner: rossonet
--

COPY public.r_version (id_version, major_version, minor_version, upgrade_date, is_upgrade) FROM stdin;
1	5	0	2022-07-19 18:04:35.776	f
\.


--
-- Name: r_cluster_id_cluster_seq; Type: SEQUENCE SET; Schema: public; Owner: rossonet
--

SELECT pg_catalog.setval('public.r_cluster_id_cluster_seq', 1, false);


--
-- Name: r_cluster_slave_id_cluster_slave_seq; Type: SEQUENCE SET; Schema: public; Owner: rossonet
--

SELECT pg_catalog.setval('public.r_cluster_slave_id_cluster_slave_seq', 1, false);


--
-- Name: r_condition_id_condition_seq; Type: SEQUENCE SET; Schema: public; Owner: rossonet
--

SELECT pg_catalog.setval('public.r_condition_id_condition_seq', 1, false);


--
-- Name: r_database_attribute_id_database_attribute_seq; Type: SEQUENCE SET; Schema: public; Owner: rossonet
--

SELECT pg_catalog.setval('public.r_database_attribute_id_database_attribute_seq', 1, false);


--
-- Name: r_database_contype_id_database_contype_seq; Type: SEQUENCE SET; Schema: public; Owner: rossonet
--

SELECT pg_catalog.setval('public.r_database_contype_id_database_contype_seq', 1, false);


--
-- Name: r_database_id_database_seq; Type: SEQUENCE SET; Schema: public; Owner: rossonet
--

SELECT pg_catalog.setval('public.r_database_id_database_seq', 1, false);


--
-- Name: r_database_type_id_database_type_seq; Type: SEQUENCE SET; Schema: public; Owner: rossonet
--

SELECT pg_catalog.setval('public.r_database_type_id_database_type_seq', 1, false);


--
-- Name: r_dependency_id_dependency_seq; Type: SEQUENCE SET; Schema: public; Owner: rossonet
--

SELECT pg_catalog.setval('public.r_dependency_id_dependency_seq', 1, false);


--
-- Name: r_directory_id_directory_seq; Type: SEQUENCE SET; Schema: public; Owner: rossonet
--

SELECT pg_catalog.setval('public.r_directory_id_directory_seq', 1, false);


--
-- Name: r_element_attribute_id_element_attribute_seq; Type: SEQUENCE SET; Schema: public; Owner: rossonet
--

SELECT pg_catalog.setval('public.r_element_attribute_id_element_attribute_seq', 1, false);


--
-- Name: r_element_id_element_seq; Type: SEQUENCE SET; Schema: public; Owner: rossonet
--

SELECT pg_catalog.setval('public.r_element_id_element_seq', 1, false);


--
-- Name: r_element_type_id_element_type_seq; Type: SEQUENCE SET; Schema: public; Owner: rossonet
--

SELECT pg_catalog.setval('public.r_element_type_id_element_type_seq', 1, false);


--
-- Name: r_job_attribute_id_job_attribute_seq; Type: SEQUENCE SET; Schema: public; Owner: rossonet
--

SELECT pg_catalog.setval('public.r_job_attribute_id_job_attribute_seq', 1, false);


--
-- Name: r_job_hop_id_job_hop_seq; Type: SEQUENCE SET; Schema: public; Owner: rossonet
--

SELECT pg_catalog.setval('public.r_job_hop_id_job_hop_seq', 1, false);


--
-- Name: r_job_id_job_seq; Type: SEQUENCE SET; Schema: public; Owner: rossonet
--

SELECT pg_catalog.setval('public.r_job_id_job_seq', 1, false);


--
-- Name: r_job_lock_id_job_lock_seq; Type: SEQUENCE SET; Schema: public; Owner: rossonet
--

SELECT pg_catalog.setval('public.r_job_lock_id_job_lock_seq', 1, false);


--
-- Name: r_jobentry_attribute_id_jobentry_attribute_seq; Type: SEQUENCE SET; Schema: public; Owner: rossonet
--

SELECT pg_catalog.setval('public.r_jobentry_attribute_id_jobentry_attribute_seq', 1, false);


--
-- Name: r_jobentry_copy_id_jobentry_copy_seq; Type: SEQUENCE SET; Schema: public; Owner: rossonet
--

SELECT pg_catalog.setval('public.r_jobentry_copy_id_jobentry_copy_seq', 1, false);


--
-- Name: r_jobentry_id_jobentry_seq; Type: SEQUENCE SET; Schema: public; Owner: rossonet
--

SELECT pg_catalog.setval('public.r_jobentry_id_jobentry_seq', 1, false);


--
-- Name: r_jobentry_type_id_jobentry_type_seq; Type: SEQUENCE SET; Schema: public; Owner: rossonet
--

SELECT pg_catalog.setval('public.r_jobentry_type_id_jobentry_type_seq', 1, false);


--
-- Name: r_log_id_log_seq; Type: SEQUENCE SET; Schema: public; Owner: rossonet
--

SELECT pg_catalog.setval('public.r_log_id_log_seq', 1, false);


--
-- Name: r_loglevel_id_loglevel_seq; Type: SEQUENCE SET; Schema: public; Owner: rossonet
--

SELECT pg_catalog.setval('public.r_loglevel_id_loglevel_seq', 1, false);


--
-- Name: r_namespace_id_namespace_seq; Type: SEQUENCE SET; Schema: public; Owner: rossonet
--

SELECT pg_catalog.setval('public.r_namespace_id_namespace_seq', 1, false);


--
-- Name: r_note_id_note_seq; Type: SEQUENCE SET; Schema: public; Owner: rossonet
--

SELECT pg_catalog.setval('public.r_note_id_note_seq', 1, false);


--
-- Name: r_partition_id_partition_seq; Type: SEQUENCE SET; Schema: public; Owner: rossonet
--

SELECT pg_catalog.setval('public.r_partition_id_partition_seq', 1, false);


--
-- Name: r_partition_schema_id_partition_schema_seq; Type: SEQUENCE SET; Schema: public; Owner: rossonet
--

SELECT pg_catalog.setval('public.r_partition_schema_id_partition_schema_seq', 1, false);


--
-- Name: r_repository_log_id_repository_log_seq; Type: SEQUENCE SET; Schema: public; Owner: rossonet
--

SELECT pg_catalog.setval('public.r_repository_log_id_repository_log_seq', 1, false);


--
-- Name: r_slave_id_slave_seq; Type: SEQUENCE SET; Schema: public; Owner: rossonet
--

SELECT pg_catalog.setval('public.r_slave_id_slave_seq', 1, false);


--
-- Name: r_step_attribute_id_step_attribute_seq; Type: SEQUENCE SET; Schema: public; Owner: rossonet
--

SELECT pg_catalog.setval('public.r_step_attribute_id_step_attribute_seq', 1, false);


--
-- Name: r_step_id_step_seq; Type: SEQUENCE SET; Schema: public; Owner: rossonet
--

SELECT pg_catalog.setval('public.r_step_id_step_seq', 1, false);


--
-- Name: r_step_type_id_step_type_seq; Type: SEQUENCE SET; Schema: public; Owner: rossonet
--

SELECT pg_catalog.setval('public.r_step_type_id_step_type_seq', 1, false);


--
-- Name: r_trans_attribute_id_trans_attribute_seq; Type: SEQUENCE SET; Schema: public; Owner: rossonet
--

SELECT pg_catalog.setval('public.r_trans_attribute_id_trans_attribute_seq', 1, false);


--
-- Name: r_trans_cluster_id_trans_cluster_seq; Type: SEQUENCE SET; Schema: public; Owner: rossonet
--

SELECT pg_catalog.setval('public.r_trans_cluster_id_trans_cluster_seq', 1, false);


--
-- Name: r_trans_hop_id_trans_hop_seq; Type: SEQUENCE SET; Schema: public; Owner: rossonet
--

SELECT pg_catalog.setval('public.r_trans_hop_id_trans_hop_seq', 1, false);


--
-- Name: r_trans_lock_id_trans_lock_seq; Type: SEQUENCE SET; Schema: public; Owner: rossonet
--

SELECT pg_catalog.setval('public.r_trans_lock_id_trans_lock_seq', 1, false);


--
-- Name: r_trans_partition_schema_id_trans_partition_schema_seq; Type: SEQUENCE SET; Schema: public; Owner: rossonet
--

SELECT pg_catalog.setval('public.r_trans_partition_schema_id_trans_partition_schema_seq', 1, false);


--
-- Name: r_trans_slave_id_trans_slave_seq; Type: SEQUENCE SET; Schema: public; Owner: rossonet
--

SELECT pg_catalog.setval('public.r_trans_slave_id_trans_slave_seq', 1, false);


--
-- Name: r_transformation_id_transformation_seq; Type: SEQUENCE SET; Schema: public; Owner: rossonet
--

SELECT pg_catalog.setval('public.r_transformation_id_transformation_seq', 1, false);


--
-- Name: r_user_id_user_seq; Type: SEQUENCE SET; Schema: public; Owner: rossonet
--

SELECT pg_catalog.setval('public.r_user_id_user_seq', 1, false);


--
-- Name: r_value_id_value_seq; Type: SEQUENCE SET; Schema: public; Owner: rossonet
--

SELECT pg_catalog.setval('public.r_value_id_value_seq', 1, false);


--
-- Name: r_version_id_version_seq; Type: SEQUENCE SET; Schema: public; Owner: rossonet
--

SELECT pg_catalog.setval('public.r_version_id_version_seq', 1, false);


--
-- PostgreSQL database dump complete
--

